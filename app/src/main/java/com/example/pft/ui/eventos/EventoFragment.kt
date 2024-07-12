package com.example.pft.ui.eventos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import com.example.pft.ApiService
import com.example.pft.R
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.Evento
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit


class EventoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_evento, container, false)
        val listaEventos=view.findViewById<ListView>(R.id.listaEventos)
        val layoutAnalista = view.findViewById<LinearLayout>(R.id.fragmentEvento_analista)
        val btnAgregar = view.findViewById<FloatingActionButton>(R.id.fragmentEvento_agregar)


        // Configurar la visibilidad del layout basado en la condición del usuario
        if (UsuarioSingleton.usuario?.rol?.nombre=="ANALISTA") {
            layoutAnalista.visibility = View.VISIBLE
            btnAgregar.visibility=View.VISIBLE
        } else {
            layoutAnalista.visibility = View.GONE
            btnAgregar.visibility = View.GONE

        }


        Log.d("EventoFragment", "onCreateView")

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")  // Reemplaza "tu_direccion_ip" con la dirección IP de tu máquina de desarrollo
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Tiempo máximo de conexión
                    .readTimeout(30, TimeUnit.SECONDS)    // Tiempo máximo de lectura
                    .writeTimeout(30, TimeUnit.SECONDS)   // Tiempo máximo de escritura
                    .build()
            )
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.obtenerEventos()

        val evento = Intent(requireContext(), EventoActivity::class.java)

        Log.d("EventoFragment", "Before API call")

        call.enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if (response.isSuccessful) {
                    val eventos = response.body()!!
                    Log.d("EventoFragment", "API call successful. Eventos: $eventos")

                    // Configurar el ArrayAdapter
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        eventos.map { evento ->
                            val timestampInicio = evento.inicio
                            val timestampFin = evento.fin

                            // Convertir timestamps a fechas
                            val fechaInicio = Date(timestampInicio)
                            val fechaFin = Date(timestampFin)

                            // Define el formato que deseas para la fecha
                            val formato = SimpleDateFormat("dd/MM/yyyy HH:mm")

                            // Formatear las fechas a String legible
                            val fechaInicioFormateada = formato.format(fechaInicio)
                            val fechaFinFormateada = formato.format(fechaFin)

                            // Construir el texto para cada evento con la fecha formateada
                            "${evento.titulo}\n${evento.modalidadEvento.nombre}\nInicio: $fechaInicioFormateada\nFin: $fechaFinFormateada"
                        }
                    )

                    // Asignar el adapter al ListView
                    listaEventos.adapter = adapter

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
        btnAgregar.setOnClickListener{
            val agregarEventoActivity = Intent(requireContext(), AgregarEventoActivity::class.java)
            startActivity(agregarEventoActivity)
        }

        return view
    }
}