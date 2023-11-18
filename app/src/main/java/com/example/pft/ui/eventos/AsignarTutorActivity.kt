package com.example.pft.ui.eventos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.example.pft.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AsignarTutorActivity : AppCompatActivity() {

    private lateinit var nombre: EditText;
    private lateinit var apellido: EditText;
    private lateinit var btnFiltrar: Button;
    private lateinit var listaTutores: ListView;
    private lateinit var listaTutoresAsignados: ListView;
    private lateinit var btnConfirmar: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignar_tutor)

        nombre=findViewById(R.id.asignarTutor_nombre)
        apellido=findViewById(R.id.asignarTutor_apellido)
        btnFiltrar=findViewById(R.id.asignarTutor_btnFiltrar)
        listaTutores=findViewById(R.id.asignarTutor_listaTutores)
        listaTutoresAsignados=findViewById(R.id.asignarTutor_listaTutoresAsignados)
        btnConfirmar=findViewById(R.id.asignarTutor_btnConfirmar)

        btnConfirmar.setOnClickListener{
            val agregarEventoActivity = Intent(this, AgregarEventoActivity::class.java)
            startActivity(agregarEventoActivity)
        }
    }
}