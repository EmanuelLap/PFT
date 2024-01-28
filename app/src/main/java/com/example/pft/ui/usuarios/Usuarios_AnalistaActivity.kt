package com.example.pft.ui.usuarios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import com.example.pft.ApiService
import com.example.pft.MainActivity
import com.example.pft.MainActivity_analista
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.entidades.Evento
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Usuarios_AnalistaActivity : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var itr: Spinner
    private lateinit var rol: Spinner
    private lateinit var listaUsuarios: ListView
    private lateinit var filtrar: Button
    private lateinit var volver: FloatingActionButton
    private lateinit var usuarios: List<Usuario>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios_analista)

        nombre=findViewById(R.id.Usuarios_AnalistaActivity_nombre)
        apellido=findViewById(R.id.Usuarios_AnalistaActivity_apellido)
        itr=findViewById(R.id.Usuarios_AnalistaActivity_itr)
        rol=findViewById(R.id.Usuarios_AnalistaActivity_rol)
        listaUsuarios=findViewById(R.id.Usuarios_AnalistaActivity_lista)
        filtrar=findViewById(R.id.Usuarios_AnalistaActivity_btnFiltrar)
        volver=findViewById(R.id.Usuarios_AnalistaActivity_volver)
        usuarios=ArrayList()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call=apiService.obtenerUsuarios()

        call.enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    usuarios = response.body()!!

                    // Configurar el ArrayAdapter
                    val adapter = ArrayAdapter(
                        this@Usuarios_AnalistaActivity,
                        android.R.layout.simple_list_item_1,
                        usuarios?.map { "${it.nombres} ${it.apellidos}\nRol: ${it.rol} _ ITR: ${it.itr}"  } ?: emptyList()
                    )

                    // Asignar el adapter al ListView
                    listaUsuarios.adapter = adapter
                    /*
                                        // Al realizar click en cualquier elemento de la lista
                                        listaEventos.setOnItemClickListener { adapterView, view, i, l ->
                                            val eventoSeleccionado = eventos!!.get(i)

                                            // Convierte el objeto Evento a una cadena JSON (por ejemplo, utilizando Gson)
                                            val eventoJson = Gson().toJson(eventoSeleccionado)

                                            // Crea un Intent y agrega la cadena JSON como extra
                                            evento.putExtra("evento", eventoJson)

                                            // Iniciar la actividad con el Intent configurado
                                            startActivity(evento)
                                        }

                     */



                }


                else {

                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                Log.e("UsuariosAnalistaActivity", "API call failed", t)
            }
        })

        val listaRol = ArrayList<String>()
        // Agrego elementos
        listaRol.add("ESTUDIANTE")
        listaRol.add("ANALISTA")
        listaRol.add("TUTOR")

        val rolAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,listaRol)
        rol.adapter=rolAdapter

        // En tu método onCreate o donde configuras el Spinner y la lista de eventos
        rol.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = listaRol[position]
                when (selectedItem) {
                    "ESTUDIANTE" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaUsuariosPorRol("ESTUDIANTE", usuarios)
                    }

                    "ANALISTA" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaUsuariosPorRol("ANALISTA", usuarios)
                    }

                    "TUTOR" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaUsuariosPorRol("TUTOR", usuarios)
                    }

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Acciones cuando no se ha seleccionado nada
            }
        }

        volver.setOnClickListener{
            val mainActivity = Intent(this, MainActivity_analista::class.java)
            startActivity(mainActivity)
        }



    }

    // Esta función filtra los eventos por tipo y actualiza el adaptador del ListView
    fun actualizarListaUsuariosPorRol(rol: String, usuarios: List<Usuario>) {
        val usuariosFiltrados = usuarios.filter { it.rol.nombre == rol }

        val adapter = ArrayAdapter(
            this@Usuarios_AnalistaActivity,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "${it.nombres} ${it.apellidos}\nRol: ${it.rol} _ ITR: ${it.itr}" } ?: emptyList()
        )
        listaUsuarios.adapter = adapter
    }
}