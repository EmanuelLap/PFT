package com.example.pft.ui.reclamos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import com.example.pft.ApiService
import com.example.pft.MainActivity_analista
import com.example.pft.R
import com.example.pft.entidades.Evento
import com.example.pft.entidades.Reclamo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Reclamo_analistaActivity : AppCompatActivity() {

    private lateinit var usuario: Spinner
    private lateinit var estado: Spinner
    private lateinit var listaReclamos: ListView
    private lateinit var volver: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclamo_analista)

        usuario=findViewById(R.id.reclamoAnalistaActivity_usuario)
        estado=findViewById(R.id.reclamoAnalista_estado)
        listaReclamos=findViewById(R.id.reclamoAnalista_lista)
        volver=findViewById(R.id.reclamoAnalista_volver)

        //spinner estado

        val estadoOpciones = ArrayList<String>()
        estadoOpciones.add("Ingresado")
        estadoOpciones.add("En proceso")
        estadoOpciones.add("Finalizado")


        // Crear un ArrayAdapter y establecerlo en el Spinner
        val estadoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estadoOpciones)
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        estado.adapter = estadoAdapter

        estado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                // Manejar la selección aquí
                val opcionSeleccionada = estadoOpciones[position]
                when (opcionSeleccionada) {
                    "Ingresado" -> {
                        // Realizar acciones específicas para "Ingresado"
                    }

                    "En proceso" -> {
                        // Realizar acciones específicas para "En proceso"
                    }

                    "Finalizado" -> {
                        // Realizar acciones específicas para "Finalizado"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Se llama cuando no se ha seleccionado nada
            }

        }




        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call=apiService.obtenerReclamos()
        Log.d("ReclamoAnalistaActivity", "Before API call")

        val reclamo = Intent(this,ReclamoSel_analistaActivity::class.java)


        call.enqueue(object : Callback<List<Reclamo>> {
            override fun onResponse(call: Call<List<Reclamo>>, response: Response<List<Reclamo>>) {
                if (response.isSuccessful) {
                    val reclamos = response.body()
                    Log.d("ReclamoAnalistaActivity", "API call successful. Reclamos: $reclamos")

                    // Configurar el ArrayAdapter
                    val adapter = ArrayAdapter(
                        this@Reclamo_analistaActivity,
                        android.R.layout.simple_list_item_1,
                        reclamos?.map { "${it.titulo}\n${it.estudianteId.nombres} ${it.estudianteId.apellidos}\nSemestre ${it.semestre}"  } ?: emptyList()
                    )

                    // Asignar el adapter al ListView
                    listaReclamos.adapter = adapter

                                        // Al realizar click en cualquier elemento de la lista
                                        listaReclamos.setOnItemClickListener { adapterView, view, i, l ->
                                            val reclamoSeleccionado = reclamos!!.get(i)

                                            // Convierte el objeto Evento a una cadena JSON (por ejemplo, utilizando Gson)
                                            val eventoJson = Gson().toJson(reclamoSeleccionado)

                                            // Crea un Intent y agrega la cadena JSON como extra
                                            reclamo.putExtra("evento", eventoJson)

                                            // Iniciar la actividad con el Intent configurado
                                            startActivity(reclamo)
                                        }
                }
                else {

                    Log.e("ReclamoAnalistaActivity", "API call failed with code ${response.code()}")
                    // Resto del código para manejar errores...
                }
            }

            override fun onFailure(call: Call<List<Reclamo>>, t: Throwable) {
                Log.e("ReclamoAnalistaActivity", "API call failed", t)
                // Resto del código para manejar errores...
            }
        })

        volver.setOnClickListener{
            val mainActivity = Intent(this, MainActivity_analista::class.java)
            startActivity(mainActivity)
        }

    }


}