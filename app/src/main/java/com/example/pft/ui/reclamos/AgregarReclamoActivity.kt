package com.example.pft.ui.reclamos

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pft.ApiService
import com.example.pft.MainActivity
import com.example.pft.R
import com.example.pft.entidades.Evento
import com.example.pft.ui.eventos.EventoActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar

class AgregarReclamoActivity : AppCompatActivity() {

    private lateinit var titulo: EditText
    private lateinit var descripcion: EditText
    private lateinit var listaEventos: Spinner
    private lateinit var creditos: EditText
    private lateinit var fechaButton: Button
    private lateinit var fechaText: TextView
    private lateinit var semestre: Spinner
    private lateinit var agregarReclamo: FloatingActionButton
    private lateinit var volver: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_reclamo)

        //Declaro rutas a elementos
        titulo=findViewById(R.id.agregarReclamoActivity_Titulo)
        descripcion=findViewById(R.id.agregarReclamoActivity_Descripcion)
        listaEventos=findViewById(R.id.agregarReclamoActivity_evento)
        creditos=findViewById(R.id.agregarReclamoActivity_creditos)
        fechaButton = findViewById(R.id.agregarReclamoActivity_fecha)
        fechaText=findViewById(R.id.agregarReclamoActivity_fecha_seleccionada)
        semestre=findViewById(R.id.agregarReclamoActivity_semestre)
        agregarReclamo=findViewById(R.id.agregarReclamoActivity_agregar)
        volver=findViewById(R.id.agregarReclamoActivity_volver)

        //spinner semestre
        // opciones (del 1 al 10)
        val semestreOpciones = ArrayList<String>()
        semestreOpciones.add("1")
        semestreOpciones.add("2")
        semestreOpciones.add("3")
        semestreOpciones.add("4")
        semestreOpciones.add("5")
        semestreOpciones.add("6")
        semestreOpciones.add("7")
        semestreOpciones.add("8")
        semestreOpciones.add("9")
        semestreOpciones.add("10")

        // Crear un ArrayAdapter y establecerlo en el Spinner
        val semestreAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, semestreOpciones)
        semestreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        semestre.adapter = semestreAdapter

        // Establecer el "hint" en el Spinner
        semestre.prompt = "Selecciona un semestre"


        //Spinner eventos

        Log.d("AgregarReclamoActivity", "onCreateView")

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")  // Reemplaza "tu_direccion_ip" con la dirección IP de tu máquina de desarrollo
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.obtenerEventos()

        val evento = Intent(this, AgregarReclamoActivity::class.java)

        Log.d("AgregarReclamoActivity", "Before API call")

        call.enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if (response.isSuccessful) {
                    val eventos = response.body()
                    Log.d("AgregarReclamoActivity", "API call successful. Eventos: $eventos")

                    // Configurar el ArrayAdapter
                    val adapter = ArrayAdapter(
                        this@AgregarReclamoActivity,
                        android.R.layout.simple_list_item_1,
                        eventos?.map { "${it.titulo}_ Modalidad: ${it.modalidadEvento.nombre}_ Inicio: ${it.inicio.toString()}"  } ?: emptyList()
                    )

                    // Asignar el adapter al ListView
                    listaEventos.adapter = adapter

                } else {
                    Log.e("EventoFragment", "API call failed with code ${response.code()}")
                    // Resto del código para manejar errores...
                }
            }

            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                Log.e("EventoFragment", "API call failed", t)
                // Resto del código para manejar errores...
            }
        })

        //---------------------------------------------------------------


        fechaButton.setOnClickListener {
            mostrarCalendario()
        }

        volver.setOnClickListener{
            val mainActivity = Intent(this@AgregarReclamoActivity, MainActivity::class.java)
            startActivity(mainActivity)
        }

    }

    private fun mostrarCalendario() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->

                val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                fechaText.text = fechaSeleccionada
            },
            year, month, day
        )
        datePickerDialog.show()
    }



}
