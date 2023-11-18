package com.example.pft.ui.reclamos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.pft.MainActivity_analista
import com.example.pft.R
import com.example.pft.entidades.Reclamo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

private lateinit var titulo: TextView;
private lateinit var detalle: TextView;
private lateinit var estado: TextView;
private lateinit var estadoActualizar: Spinner;
private lateinit var respuesta: EditText
private lateinit var btn_actualizar: Button
private lateinit var btn_volver: FloatingActionButton
class ReclamoSel_analistaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclamo_sel_analista)

        val reclamoJson = intent.getStringExtra("reclamo")

        // Convertir la cadena JSON de vuelta a un objeto Evento (usando Gson)
        val reclamoSeleccionado = Gson().fromJson(reclamoJson, Reclamo::class.java)

        titulo=findViewById(R.id.reclamoSel_titulo)
        detalle=findViewById(R.id.reclamoSel_detalle)
        estado=findViewById(R.id.reclamoSel_estado)
        estadoActualizar=findViewById(R.id.reclamoSel_actEstado)
        respuesta=findViewById(R.id.reclamoSel_respuesta)
        btn_actualizar=findViewById(R.id.reclamoSel_btnModificarReclamo)
        btn_volver=findViewById(R.id.reclamoSel_btnVolver)


        titulo.text="Título: ${reclamoSeleccionado.titulo}"
        detalle.text="Detalle: ${reclamoSeleccionado.detalle}"
        estado.text = "Estado: ${reclamoSeleccionado.activo?.toString() ?: "Esperando respuesta"}"


        //spinner estado

        val estadoOpciones = ArrayList<String>()
        estadoOpciones.add("Ingresado")
        estadoOpciones.add("En proceso")
        estadoOpciones.add("Finalizado")


        // Crear un ArrayAdapter y establecerlo en el Spinner
        val estadoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estadoOpciones)
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        estadoActualizar.adapter = estadoAdapter

        estadoActualizar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        btn_volver.setOnClickListener{
            val mainActivity = Intent(this@ReclamoSel_analistaActivity, MainActivity_analista::class.java)
            startActivity(mainActivity)
        }

    }
}