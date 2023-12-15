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
    private lateinit var eventos: List<Evento>

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
        eventos=ArrayList()

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
                    eventos = response.body()!!
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
        listaTipo.add("Defensa de Proyecto")
        listaTipo.add("Jornada presencial")
        listaTipo.add("Prueba final")
        listaTipo.add("Examen")


        val tipoAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,listaTipo)
        tipo.adapter=tipoAdapter

        // En tu método onCreate o donde configuras el Spinner y la lista de eventos
        tipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = listaTipo[position]
                when (selectedItem) {
                    "Defensa de Proyecto" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorTipo("Defensa de Proyecto", eventos)
                    }

                    "Jornada presencial" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorTipo("Jornada presencial", eventos)
                    }

                    "Prueba final" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorTipo("Prueba final", eventos)
                    }

                    "Examen" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorTipo("Examen", eventos)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Acciones cuando no se ha seleccionado nada
            }
        }


       //Modalidad

        // Creo lista de modalidades de evento
        val listaModalidad = ArrayList<String>()
        // Agrego elementos
        listaModalidad.add("Virtual")
        listaModalidad.add("Presencial")
        listaModalidad.add("Semipresencial")

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
                    "Virtual" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorModalidad("Virtual", eventos)
                    }

                    "Presencial" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorModalidad("Presencial", eventos)
                    }

                    "Semipresencial" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorModalidad("Semipresencial", eventos)
                    }

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Acciones cuando no se ha seleccionado nada
            }
        }

        //ITR

        // Creo lista de ITR
        val listaItr = ArrayList<String>()
        // Agrego elementos
        listaItr.add("Durazno [Centro Sur]")
        listaItr.add("FrayBentos [ITR Suroeste]")
        listaItr.add("Sede Utec San Jose [ITR Suroeste]")
        listaItr.add("Cure de Maldonado [ITR Suroeste]")
        listaItr.add("La paz [ITR Suroeste]")
        listaItr.add("Sede Mercedes [ITR Suroeste]")
        listaItr.add("Nueva Helvecia [ITR Suroeste]")
        listaItr.add("Polo tecnologico Paysandu [ITR Suroeste]")
        listaItr.add("Rivera [ITR Norte]")
        listaItr.add("Minas [ITR Este]")

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
                    "Durazno [Centro Sur]" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorItr("Durazno [Centro Sur]", eventos)
                    }

                    "FrayBentos [ITR Suroeste]" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorItr("FrayBentos [ITR Suroeste]", eventos)
                    }

                    "Sede Utec San Jose [ITR Suroeste]" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorItr("Sede Utec San Jose [ITR Suroeste]", eventos)
                    }

                    "Cure de Maldonado [ITR Suroeste]" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorItr("Cure de Maldonado [ITR Suroeste]", eventos)
                    }

                    "La paz [ITR Suroeste]" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorItr("La paz [ITR Suroeste]", eventos)
                    }

                    "Sede Mercedes [ITR Suroeste]" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorItr("Sede Mercedes [ITR Suroeste]", eventos)
                    }

                    "Nueva Helvecia [ITR Suroeste]" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorItr("Nueva Helvecia [ITR Suroeste]", eventos)
                    }

                    "Polo tecnologico Paysandu [ITR Suroeste]" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorItr("Polo tecnologico Paysandu [ITR Suroeste]", eventos)
                    }

                    "Rivera [ITR Norte]" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorItr("Rivera [ITR Norte]", eventos)
                    }

                    "Minas [ITR Este]" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaEventosPorItr("Minas [ITR Este]", eventos)
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


    // Esta función filtra los eventos por tipo y actualiza el adaptador del ListView
    fun actualizarListaEventosPorTipo(tipoSeleccionado: String, eventos: List<Evento>) {
        val eventosFiltrados = eventos.filter { it.tipoEvento.nombre == tipoSeleccionado }

        val adapter = ArrayAdapter(
            this@Evento_analistaActivity,
            android.R.layout.simple_list_item_1,
            eventosFiltrados.map { "${it.titulo}\n${it.modalidadEvento.nombre}\nInicio: ${it.inicio.toString()}" } ?: emptyList()
        )
        listaEventos.adapter = adapter
    }

    fun actualizarListaEventosPorModalidad(modalidad: String, eventos: List<Evento>) {
        val eventosFiltrados = eventos.filter { it.modalidadEvento.nombre == modalidad }

        val adapter = ArrayAdapter(
            this@Evento_analistaActivity,
            android.R.layout.simple_list_item_1,
            eventosFiltrados.map { "${it.titulo}\n${it.modalidadEvento.nombre}\nInicio: ${it.inicio.toString()}" } ?: emptyList()
        )
        listaEventos.adapter = adapter
    }

    fun actualizarListaEventosPorItr(itr: String, eventos: List<Evento>) {
        val eventosFiltrados = eventos.filter { it.itrDTO.nombre == itr }

        val adapter = ArrayAdapter(
            this@Evento_analistaActivity,
            android.R.layout.simple_list_item_1,
            eventosFiltrados.map { "${it.titulo}\n${it.modalidadEvento.nombre}\nInicio: ${it.inicio.toString()}" } ?: emptyList()
        )
        listaEventos.adapter = adapter
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