package com.example.pft.ui.eventos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.pft.MainActivity
import com.example.pft.R
import com.example.pft.entidades.Evento
import com.example.pft.ui.login.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class EventoActivity : AppCompatActivity() {

    private lateinit var titulo:TextView;
    private lateinit var tipo:TextView;
    private lateinit var modalidad:TextView;
    private lateinit var localizacion:TextView;
    private lateinit var inicio:TextView;
    private lateinit var fin:TextView;
    private lateinit var btn_reclamo:Button
    private lateinit var btn_volver:FloatingActionButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evento)

        // Recuperar la cadena JSON del Intent
        val eventoJson = intent.getStringExtra("evento")

        // Convertir la cadena JSON de vuelta a un objeto Evento (usando Gson)
        val eventoSeleccionado = Gson().fromJson(eventoJson, Evento::class.java)

        titulo=findViewById(R.id.evento_titulo)
        tipo=findViewById(R.id.evento_tipo)
        modalidad=findViewById(R.id.evento_modalidad)
        localizacion=findViewById(R.id.evento_localizacion)
        inicio=findViewById(R.id.evento_inicio)
        fin=findViewById(R.id.evento_fin)
        btn_volver=findViewById(R.id.evento_btnVolver)

        titulo.text=eventoSeleccionado.titulo
        tipo.text="Tipo: ${eventoSeleccionado.tipoEvento.nombre}"
        modalidad.text="Modalidad: ${eventoSeleccionado.modalidadEvento.nombre}"
        localizacion.text="Localización: ${eventoSeleccionado.localizacion}"
        inicio.text="Inicio: ${eventoSeleccionado.inicio.toString()}"
        fin.text="Fin: ${eventoSeleccionado.fin.toString()}"


        btn_volver.setOnClickListener{
            val mainActivity = Intent(this@EventoActivity, MainActivity::class.java)
            startActivity(mainActivity)
        }



    }


}