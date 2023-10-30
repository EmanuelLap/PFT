package com.example.pft.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pft.EventoAdapter
import com.example.pft.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RegistroActivity : AppCompatActivity() {

    private lateinit var btnVolver: FloatingActionButton
    private lateinit var btnConfirmar:FloatingActionButton
    private lateinit var documento: EditText
    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var nombreUsuario: EditText
    private lateinit var contrasena: EditText
    private lateinit var contrasenaConfirmar: EditText
    private lateinit var emailInsitucional: EditText
    private lateinit var emailPersonal: EditText
    private lateinit var emailPersonalConfirmar: EditText
    private lateinit var telefono: EditText
    private lateinit var genero: EditText
    private lateinit var fecNac: EditText
    private lateinit var itr: EditText
    private lateinit var departamento: EditText
    private lateinit var localidad: EditText
    private lateinit var tipousuario: Spinner
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

       btnVolver=findViewById(R.id.registro_btnVolver)
       btnConfirmar=findViewById(R.id.registro_btnConfirmar)
       documento=findViewById(R.id.registro_documento)
       nombre=findViewById(R.id.registro_nombre)
       apellido=findViewById(R.id.registro_apellido)
       nombreUsuario=findViewById(R.id.registro_nombre_usuario)
       contrasena=findViewById(R.id.registro_contrasena)
       contrasenaConfirmar=findViewById(R.id.registro_confirmar_contrasena)
       emailInsitucional=findViewById(R.id.registro_email_institucional)
       emailPersonal=findViewById(R.id.registro_email_personal)
       emailPersonalConfirmar=findViewById(R.id.registro_confirmar_email_personal)
       telefono=findViewById(R.id.registro_telefono)
       genero=findViewById(R.id.registro_genero)
       fecNac=findViewById(R.id.registro_fec_nac)
       itr=findViewById(R.id.registro_itr)
       departamento=findViewById(R.id.registro_departamento)
       localidad=findViewById(R.id.registro_localidad)
       tipousuario=findViewById(R.id.registro_tipo)
       recyclerView=findViewById(R.id.registro_recyclerView_estudiante)

        // Creo lista de tipos de usuario
        val lista = ArrayList<String>()
        // Agregar elementos a listaDeDatos aquí
        lista.add("Estudiante")
        lista.add("Tutor")
        lista.add("Analista")

        val tipoUsuarioAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,lista)
        tipousuario.adapter=tipoUsuarioAdapter

        tipousuario.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = lista[position]
                when (selectedItem) {
                    "Estudiante" -> {
                        recyclerView.layoutManager = LinearLayoutManager(this@RegistroActivity)
                        val adapter = RegistroAdapter_estudiante()
                        recyclerView.adapter = adapter
                    }

                    "Tutor" -> {
                        recyclerView.layoutManager = LinearLayoutManager(this@RegistroActivity)
                        val adapter = RegistroAdapter_tutor()
                        recyclerView.adapter = adapter
                    }

                    "Analista" -> {
                        recyclerView.layoutManager = LinearLayoutManager(this@RegistroActivity)
                        val adapter = RegistroAdapter_analista()
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

            /*
            // Configurar el RecyclerView con un LinearLayoutManager
            recyclerView.layoutManager = LinearLayoutManager(this)

            /*
            // Crear una lista de datos o modelos que deseas mostrar en las secciones
            val lista = ArrayList<String>()
            // Agregar elementos a listaDeDatos aquí
            lista.add("Evento 1")
            lista.add("Evento 2")
            lista.add("Evento 3")
            lista.add("Evento 4")
            // Crear un adaptador personalizado y asignarlo al RecyclerView
             */

            val adapter = RegistroAdapter_estudiante()

            recyclerView.adapter = adapter

             */

        btnVolver.setOnClickListener {
            val loginActivity = Intent(this, LoginActivity::class.java)
            startActivity(loginActivity)
        }

        btnConfirmar.setOnClickListener{

        }
    }
}