package com.example.pft.ui.reclamos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pft.R
import com.example.pft.entidades.ReclamoDTO
import com.google.gson.Gson

class ReclamoResponderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclamo_responder)

        val reclamoJson = intent.getStringExtra("reclamo")

        // Convertir la cadena JSON de vuelta a un objeto Evento (usando Gson)
        val reclamoDTOSeleccionado = Gson().fromJson(reclamoJson, ReclamoDTO::class.java)


    }
}