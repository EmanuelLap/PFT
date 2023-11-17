package com.example.pft.ui.usuarios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import com.example.pft.MainActivity
import com.example.pft.MainActivity_analista
import com.example.pft.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Usuarios_AnalistaActivity : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var itr: Spinner
    private lateinit var rol: Spinner
    private lateinit var lista: ListView
    private lateinit var filtrar: Button
    private lateinit var volver: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios_analista)

        nombre=findViewById(R.id.Usuarios_AnalistaActivity_nombre)
        apellido=findViewById(R.id.Usuarios_AnalistaActivity_apellido)
        itr=findViewById(R.id.Usuarios_AnalistaActivity_itr)
        rol=findViewById(R.id.Usuarios_AnalistaActivity_rol)
        lista=findViewById(R.id.Usuarios_AnalistaActivity_lista)
        filtrar=findViewById(R.id.Usuarios_AnalistaActivity_btnFiltrar)
        volver=findViewById(R.id.Usuarios_AnalistaActivity_volver)


        volver.setOnClickListener{
            val mainActivity = Intent(this, MainActivity_analista::class.java)
            startActivity(mainActivity)
        }



    }
}