package com.example.pft.ui.eventos

import android.app.DatePickerDialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pft.ApiService
import com.example.pft.MainActivity_analista
import com.example.pft.R
import com.example.pft.entidades.Evento
import com.example.pft.ui.login.RegistroAdapter_analista
import com.example.pft.ui.login.RegistroAdapter_estudiante
import com.example.pft.ui.login.RegistroAdapter_tutor
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.Locale

class Evento_analistaActivity : AppCompatActivity() {

    private lateinit var titulo: EditText
    private lateinit var tipo: Spinner
    private lateinit var modalidad: Spinner
    private lateinit var itr: Spinner
    private lateinit var localizacion: EditText
    private lateinit var inicio: Button
    private lateinit var inicioSeleccion: TextView
    private lateinit var fin: Button
    private lateinit var finSeleccion: TextView
    private lateinit var filtrar: Button
    private lateinit var listaEventos: ListView
    private lateinit var volver: FloatingActionButton
    private lateinit var nuevo: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evento_analista)

        val locale = Locale("es", "ES")
        Locale.setDefault(locale)

        titulo=findViewById(R.id.Evento_AnalistaActivity_titulo)
        tipo=findViewById(R.id.Eventos_AnalistaActivity_tipo)
        modalidad=findViewById(R.id.Eventos_AnalistaActivity_modalidad)
        itr=findViewById(R.id.Eventos_AnalistaActivity_itr)
        localizacion=findViewById(R.id.Eventos_AnalistaActivity_localizacion)
        inicio=findViewById(R.id.Evento_AnalistaActivity_inicio)
        inicioSeleccion=findViewById(R.id.Evento_AnalistaActivity_inicio_seleccion)
        fin=findViewById(R.id.Evento_AnalistaActivity_fin)
        finSeleccion=findViewById(R.id.Evento_AnalistaActivity_fin_seleccion)
        filtrar=findViewById(R.id.Evento_AnalistaActivity_btnFiltrar)
        listaEventos=findViewById(R.id.Evento_Analista_lista)
        volver=findViewById(R.id.Evento_Analista_volver)
        nuevo=findViewById(R.id.Evento_Analista_agregar)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call=apiService.obtenerEventos()
        Log.d("EventoFragment", "Before API call")

        call.enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if (response.isSuccessful) {
                    val eventos = response.body()
                    Log.d("EventoFragment", "API call successful. Eventos: $eventos")

                    // Configurar el ArrayAdapter
                    val adapter = ArrayAdapter(
                        this@Evento_analistaActivity,
                        android.R.layout.simple_list_item_1,
                        eventos?.map { "${it.titulo}\n${it.modalidadEvento.nombre}\nInicio: ${it.inicio.toString()}"  } ?: emptyList()
                    )

                    // Asignar el adapter al ListView
                    listaEventos.adapter = adapter
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

                    Log.e("EventoFragment", "API call failed with code ${response.code()}")
                    // Resto del código para manejar errores...
                }
            }

            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                Log.e("EventoFragment", "API call failed", t)
                // Resto del código para manejar errores...
            }
        })

        //Filtros

        // Tipo

        // Creo lista de tipos de evento
        val listaTipo = ArrayList<String>()
        // Agrego elementos
        listaTipo.add("Estudiante")
        listaTipo.add("Tutor")
        listaTipo.add("Analista")

        val tipoAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,listaTipo)
        tipo.adapter=tipoAdapter

        tipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = listaTipo[position]
                when (selectedItem) {
                    "Estudiante" -> {

                    }

                    "Tutor" -> {

                    }

                    "Analista" -> {

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

       //Modalidad

        // Creo lista de modalidades de evento
        val listaModalidad = ArrayList<String>()
        // Agrego elementos
        listaModalidad.add("Estudiante")
        listaModalidad.add("Tutor")
        listaModalidad.add("Analista")

        val modalidadAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,listaModalidad)
        modalidad.adapter=modalidadAdapter

        modalidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = listaModalidad[position]
                when (selectedItem) {
                    "Estudiante" -> {

                    }

                    "Tutor" -> {

                    }

                    "Analista" -> {

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        //ITR

        // Creo lista de ITR
        val listaItr = ArrayList<String>()
        // Agrego elementos
        listaItr.add("Estudiante")
        listaItr.add("Tutor")
        listaItr.add("Analista")

        val itrAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,listaItr)
        itr.adapter=itrAdapter

        itr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = listaItr[position]
                when (selectedItem) {
                    "Estudiante" -> {

                    }

                    "Tutor" -> {

                    }

                    "Analista" -> {

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        inicio.setOnClickListener{
            mostrarCalendarioInicio()
        }

        fin.setOnClickListener{
            mostrarCalendarioFin()
        }

        volver.setOnClickListener{
            val mainActivity = Intent(this, MainActivity_analista::class.java)
            startActivity(mainActivity)
        }

        nuevo.setOnClickListener{
            val agregarEventoActivity = Intent(this, AgregarEventoActivity::class.java)
            startActivity(agregarEventoActivity)
        }
    }

    private fun mostrarCalendarioInicio() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->

                val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                inicioSeleccion.text = fechaSeleccionada
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun mostrarCalendarioFin() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->

                val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                finSeleccion.text = fechaSeleccionada
            },
            year, month, day
        )
        datePickerDialog.show()
    }


}