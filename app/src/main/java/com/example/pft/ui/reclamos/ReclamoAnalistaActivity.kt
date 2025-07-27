package com.example.pft.ui.reclamos

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.pft.ApiClient
import com.example.pft.MainActivity
import com.example.pft.R
import com.example.pft.entidades.ReclamoDTO
import com.example.pft.entidades.ReclamoDTOMobile
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date
import java.util.Locale

private lateinit var titulo: TextView;
private lateinit var detalle: TextView;
private lateinit var fecha: TextView;
private lateinit var evento: TextView;
private lateinit var semestre: TextView;
private lateinit var creditos: TextView;
private lateinit var btn_responder: Button
private lateinit var btn_volver: FloatingActionButton

class ReclamoAnalistaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclamo_analista)

        val locale = Locale("es", "ES")
        Locale.setDefault(locale)

        val reclamoJson = intent.getStringExtra("reclamo")

        // Convertir la cadena JSON de vuelta a un objeto Evento (usando Gson)
        val reclamoDTOSeleccionado = Gson().fromJson(reclamoJson, ReclamoDTO::class.java)



        titulo=findViewById(R.id.reclamoSel_titulo)
        detalle=findViewById(R.id.reclamoSel_detalle)
        fecha=findViewById(R.id.reclamoSel_fecha)
        evento=findViewById(R.id.reclamoSel_evento)
        semestre=findViewById(R.id.reclamoSel_semestre)
        creditos=findViewById(R.id.reclamoSel_creditos)
        btn_volver=findViewById(R.id.reclamoSel_btnVolver)
        btn_responder=findViewById(R.id.reclamoSel_Responder)

        titulo.text="Título: ${reclamoDTOSeleccionado.titulo}"
        detalle.text="Detalle: ${reclamoDTOSeleccionado.detalle}"
        val fechaString = Date(reclamoDTOSeleccionado.fecha).toString()
        fecha.text="Fecha: ${fechaString}"
        evento.text="Evento: ${reclamoDTOSeleccionado.eventoId.titulo}"
        semestre.text="Semestre: ${reclamoDTOSeleccionado.semestre}"
        creditos.text="Créditos: ${reclamoDTOSeleccionado.creditos}"

        btn_responder.setOnClickListener{
            val responderActivity = Intent(this@ReclamoAnalistaActivity,ReclamoAccionActivity::class.java)
            responderActivity.putExtra("reclamo", reclamoJson)
            startActivity(responderActivity)
        }

        btn_volver.setOnClickListener{
            finish()
        }


    }

}