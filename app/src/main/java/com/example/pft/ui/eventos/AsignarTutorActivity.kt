package com.example.pft.ui.eventos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.example.pft.ApiService
import com.example.pft.R
import com.example.pft.Usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AsignarTutorActivity : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var btnFiltrar: Button
    private lateinit var listaTutores: ListView
    private lateinit var btnConfirmar: Button
    private lateinit var usuarios: List<Usuario>
    private lateinit var tutoresSeleccionados: MutableList<Usuario>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignar_tutor)

        nombre = findViewById(R.id.asignarTutor_nombre)
        apellido = findViewById(R.id.asignarTutor_apellido)
        btnFiltrar = findViewById(R.id.asignarTutor_btnFiltrar)
        listaTutores = findViewById(R.id.asignarTutor_listaTutores)
        listaTutores.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        btnConfirmar = findViewById(R.id.asignarTutor_btnConfirmar)
        usuarios = ArrayList()
        tutoresSeleccionados = mutableListOf()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()
            )
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        //----------Lista tutores------------------------------------

        val callUsuarios = apiService.obtenerUsuarios()

        callUsuarios.enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    usuarios = response.body() ?: emptyList()
                    Log.d("AgregarTutorActivity", "API call successful. Usuarios: $usuarios")

                    val usuariosFiltrados = usuarios.filter { it.rol.nombre == "TUTOR" }

                    val adapter = ArrayAdapter(
                        this@AsignarTutorActivity,
                        android.R.layout.simple_list_item_multiple_choice,
                        usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}" }
                    )
                    listaTutores.adapter = adapter
                } else {
                    Log.e("API_CALL", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                Log.e("UsuariosFragment", "API call failed", t)
            }
        })

        listaTutores.setOnItemClickListener { parent, view, position, id ->
            val usuarioSeleccionado = usuarios[position]
            if (!tutoresSeleccionados.contains(usuarioSeleccionado)) {
                tutoresSeleccionados.add(usuarioSeleccionado)
            }
        }

        btnConfirmar.setOnClickListener {
            // Puedes pasar la lista tutoresSeleccionados a la siguiente actividad
            Log.d("AsignarTutorActivity", "Tutores seleccionados: ${tutoresSeleccionados}")

            val intent = Intent(this@AsignarTutorActivity,AgregarEventoActivity::class.java)
            intent.putExtra("tutoresSeleccionados", ArrayList(tutoresSeleccionados))
            startActivity(intent)
        }
    }
}