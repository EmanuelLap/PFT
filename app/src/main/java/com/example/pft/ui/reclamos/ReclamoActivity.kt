package com.example.pft.ui.reclamos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.pft.MainActivity
import com.example.pft.R
import com.example.pft.entidades.Evento
import com.example.pft.entidades.Reclamo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

private lateinit var titulo: TextView;
private lateinit var detalle: TextView;
private lateinit var estado: TextView;
private lateinit var btn_modificar: Button
private lateinit var btn_volver: FloatingActionButton
class ReclamoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclamo)

        val reclamoJson = intent.getStringExtra("reclamo")

        // Convertir la cadena JSON de vuelta a un objeto Evento (usando Gson)
        val reclamoSeleccionado = Gson().fromJson(reclamoJson, Reclamo::class.java)

        titulo=findViewById(R.id.reclamoAct_titulo)
        detalle=findViewById(R.id.reclamoAct_detalle)
        estado=findViewById(R.id.reclamoAct_estado)
        btn_modificar=findViewById(R.id.reclamoAct_modificarReclamo)
        btn_volver=findViewById(R.id.reclamoAct_btnVolver)

        titulo.text="Título: ${reclamoSeleccionado.titulo}"
        detalle.text="Detalle: ${reclamoSeleccionado.detalle}"
        estado.text = "Estado: ${reclamoSeleccionado.activo?.toString() ?: "Esperando respuesta"}"



        btn_volver.setOnClickListener{
            val mainActivity = Intent(this@ReclamoActivity, MainActivity::class.java)
            startActivity(mainActivity)
        }


    }
}