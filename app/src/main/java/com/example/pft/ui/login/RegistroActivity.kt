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
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pft.EventoAdapter
import com.example.pft.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RegistroActivity : AppCompatActivity() {

    //botones
    private lateinit var btnVolver: FloatingActionButton
    private lateinit var btnConfirmar:FloatingActionButton

    //datos
    private lateinit var documento: EditText
    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var nombreUsuario: EditText
    private lateinit var contrasena: EditText
    private lateinit var contrasenaConfirmar: EditText
    private lateinit var emailInstitucional: EditText
    private lateinit var emailPersonal: EditText
    private lateinit var emailPersonalConfirmar: EditText
    private lateinit var telefono: EditText
    private lateinit var genero: EditText
    private lateinit var fecNac: EditText
    private lateinit var itr: EditText
    private lateinit var departamento: EditText
    private lateinit var localidad: EditText
    private lateinit var tipoUsuario: Spinner
    private lateinit var recyclerView: RecyclerView

    //mensajes
    private lateinit var mensaje_documento: TextView
    private lateinit var mensaje_nombre: TextView
    private lateinit var mensaje_apellido: TextView
    private lateinit var mensaje_nombreUsuario: TextView
    private lateinit var mensaje_contrasena: TextView
    private lateinit var mensaje_contrasenaConfirmar: TextView
    private lateinit var mensaje_emailInstitucional: TextView
    private lateinit var mensaje_emailPersonal: TextView
    private lateinit var mensaje_emailPersonalConfirmar: TextView
    private lateinit var mensaje_telefono: TextView
    private lateinit var mensaje_genero: TextView
    private lateinit var mensaje_fecNac: TextView
    private lateinit var mensaje_itr: TextView
    private lateinit var mensaje_departamento: TextView
    private lateinit var mensaje_localidad: TextView
    private lateinit var mensaje_tipoUsuario: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

       //Declaro rutas
       btnVolver=findViewById(R.id.registro_btnVolver)
       btnConfirmar=findViewById(R.id.registro_btnConfirmar)
       documento=findViewById(R.id.registro_documento)
       nombre=findViewById(R.id.registro_nombre)
       apellido=findViewById(R.id.registro_apellido)
       nombreUsuario=findViewById(R.id.registro_nombre_usuario)
       contrasena=findViewById(R.id.registro_contrasena)
       contrasenaConfirmar=findViewById(R.id.registro_confirmar_contrasena)
       emailInstitucional=findViewById(R.id.registro_email_institucional)
       emailPersonal=findViewById(R.id.registro_email_personal)
       emailPersonalConfirmar=findViewById(R.id.registro_confirmar_email_personal)
       telefono=findViewById(R.id.registro_telefono)
       genero=findViewById(R.id.registro_genero)
       fecNac=findViewById(R.id.registro_fec_nac)
       itr=findViewById(R.id.registro_itr)
       departamento=findViewById(R.id.registro_departamento)
       localidad=findViewById(R.id.registro_localidad)
       tipoUsuario=findViewById(R.id.registro_tipo)
       recyclerView=findViewById(R.id.registro_recyclerView_estudiante)

        // Creo lista de tipos de usuario
        val lista = ArrayList<String>()
        // Agregar elementos a listaDeDatos aquí
        lista.add("Estudiante")
        lista.add("Tutor")
        lista.add("Analista")

        val tipoUsuarioAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,lista)
        tipoUsuario.adapter=tipoUsuarioAdapter

        tipoUsuario.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

            //Declaro rutas
            mensaje_apellido=findViewById(R.id.registro_mensaje_apellido)
            mensaje_fecNac=findViewById(R.id.registro_mensaje_fec_nac)
            mensaje_contrasena=findViewById(R.id.registro_mensaje_contrasena)
            mensaje_documento=findViewById(R.id.registro_mensaje_documento)
            mensaje_departamento=findViewById(R.id.registro_mensaje_departamento)
            mensaje_contrasenaConfirmar=findViewById(R.id.registro_mensaje_confirmar_contrasena)
            mensaje_emailInstitucional=findViewById(R.id.registro_mensaje_email_institucional)
            mensaje_emailPersonal=findViewById(R.id.registro_mensaje_email_personal)
            mensaje_emailPersonalConfirmar=findViewById(R.id.registro_mensaje_confirmar_email_personal)
            mensaje_genero=findViewById(R.id.registro_mensaje_genero)
            mensaje_itr=findViewById(R.id.registro_mensaje_itr)
            mensaje_localidad=findViewById(R.id.registro_mensaje_localidad)
            mensaje_nombre=findViewById(R.id.registro_mensaje_nombre)
            mensaje_nombreUsuario=findViewById(R.id.registro_mensaje_nombre_usuario)
            mensaje_telefono=findViewById(R.id.registro_mensaje_telefono)
            mensaje_tipoUsuario=findViewById(R.id.registro_mensaje_tipo)

            val camposVacios = mutableListOf<String>()



            if (documento.text.toString().isEmpty()) {
                camposVacios.add("Documento")
                mensaje_documento.text = "Ingresa un documento"
                mensaje_documento.alpha = 0.8f
                mensaje_documento.visibility = View.VISIBLE
            } else {
                mensaje_documento.visibility = View.INVISIBLE
            }

            if (nombre.text.toString().isEmpty()) {
                camposVacios.add("Nombre")
                mensaje_nombre.text = "Ingresa un nombre"
                mensaje_nombre.alpha = 0.8f
                mensaje_nombre.visibility = View.VISIBLE
            } else {
                mensaje_nombre.visibility = View.INVISIBLE
            }

            if (apellido.text.toString().isEmpty()) {
                camposVacios.add("Apellido")
                mensaje_apellido.text = "Ingresa un apellido"
                mensaje_apellido.alpha = 0.8f
                mensaje_apellido.visibility = View.VISIBLE // Mostrar el mensaje de error
            } else {
                mensaje_apellido.visibility = View.INVISIBLE // Ocultar el mensaje de error si no está vacío
            }

            if (nombreUsuario.text.toString().isEmpty()) {
                camposVacios.add("Nombre usuario")
                mensaje_nombreUsuario.text = "Ingresa una contraseña"
                mensaje_nombreUsuario.alpha = 0.8f
                mensaje_nombreUsuario.visibility = View.VISIBLE
            } else {
                mensaje_nombreUsuario.visibility = View.INVISIBLE
            }

            if (contrasena.text.toString().isEmpty()) {
                camposVacios.add("Contraseña")
                mensaje_contrasena.text = "Ingresa una contraseña"
                mensaje_contrasena.alpha = 0.8f
                mensaje_contrasena.visibility = View.VISIBLE
            } else {
                mensaje_contrasena.visibility = View.INVISIBLE
            }

            if (contrasenaConfirmar.text.toString().isEmpty()) {
                camposVacios.add("Contraseña confirmar")
                mensaje_contrasenaConfirmar.text = "Confirma tu contraseña"
                mensaje_contrasenaConfirmar.alpha = 0.8f
                mensaje_contrasenaConfirmar.visibility = View.VISIBLE
            } else {
                mensaje_contrasenaConfirmar.visibility = View.INVISIBLE
            }

            if (emailInstitucional.text.toString().isEmpty()) {
                camposVacios.add("Email institucional")
                mensaje_emailInstitucional.text = "Ingresa un email institucional"
                mensaje_emailInstitucional.alpha = 0.8f
                mensaje_emailInstitucional.visibility = View.VISIBLE
            } else {
                mensaje_emailInstitucional.visibility = View.INVISIBLE
            }

            if (emailPersonal.text.toString().isEmpty()) {
                camposVacios.add("Email personal")
                mensaje_emailPersonal.text = "Ingresa un email personal"
                mensaje_emailPersonal.alpha = 0.8f
                mensaje_emailPersonal.visibility = View.VISIBLE
            } else {
                mensaje_emailPersonal.visibility = View.INVISIBLE
            }

            if (emailPersonalConfirmar.text.toString().isEmpty()) {
                camposVacios.add("Fecha de Nacimiento")
                mensaje_emailPersonalConfirmar.text = "Selecciona una fecha de nacimiento"
                mensaje_emailPersonalConfirmar.alpha = 0.8f
                mensaje_emailPersonalConfirmar.visibility = View.VISIBLE
            } else {
                mensaje_emailPersonalConfirmar.visibility = View.INVISIBLE
            }

            if (telefono.text.toString().isEmpty()) {
                camposVacios.add("Telefono")
                mensaje_telefono.text = "Ingresa un teléfono"
                mensaje_telefono.alpha = 0.8f
                mensaje_telefono.visibility = View.VISIBLE
            } else {
                mensaje_telefono.visibility = View.INVISIBLE
            }

            if (genero.text.toString().isEmpty()) {
                camposVacios.add("Género")
                mensaje_genero.text = "Selecciona un género"
                mensaje_genero.alpha = 0.8f
                mensaje_genero.visibility = View.VISIBLE
            } else {
                mensaje_genero.visibility = View.INVISIBLE
            }

            if (fecNac.text.toString().isEmpty()) {
                camposVacios.add("Fec nac")
                mensaje_fecNac.text = "Selecciona una fecha de nacimiento"
                mensaje_fecNac.alpha = 0.8f
                mensaje_fecNac.visibility = View.VISIBLE
            } else {
                mensaje_fecNac.visibility = View.INVISIBLE
            }

            if (itr.text.toString().isEmpty()) {
                camposVacios.add("Itr")
                mensaje_itr.text = "Selecciona un itr"
                mensaje_itr.alpha = 0.8f
                mensaje_itr.visibility = View.VISIBLE
            } else {
                mensaje_itr.visibility = View.INVISIBLE
            }

            if (departamento.text.toString().isEmpty()) {
                camposVacios.add("Departamento")
                mensaje_departamento.text = "Selecciona un departamento"
                mensaje_departamento.alpha = 0.8f
                mensaje_departamento.visibility = View.VISIBLE
            } else {
                mensaje_departamento.visibility = View.INVISIBLE
            }

            if (localidad.text.toString().isEmpty()) {
                camposVacios.add("Localidad")
                mensaje_localidad.text = "Ingresa una localidad"
                mensaje_localidad.alpha = 0.8f
                mensaje_localidad.visibility = View.VISIBLE
            } else {
                mensaje_localidad.visibility = View.INVISIBLE
            }

            if (camposVacios.isNotEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Todos los campos están completos, puedes realizar alguna acción aquí
            }
/*
            when {
                apellido.text.toString().isEmpty() -> {
                    mensaje_apellido.text = "Ingresa un apellido";
                    mensaje_apellido.alpha = 0.8f
                }
                fecNac.text.toString().isEmpty() -> {
                    mensaje_fecNac.text="Selecciona una fecha de nacimiento"
                    mensaje_fecNac.alpha = 0.8f
                }

                contrasena.text.toString().isEmpty() -> {
                    mensaje_contrasena.text = "Ingresa una contraseña"
                    mensaje_contrasena.alpha = 0.8f
                }
                contrasenaConfirmar.text.toString().isEmpty() -> {
                    mensaje_contrasenaConfirmar.text = "Confirma tu contraseña"
                    mensaje_contrasenaConfirmar.alpha = 0.8f
                }
                documento.text.toString().isEmpty() -> {
                    mensaje_documento.text = "Ingresa un documeto"
                    mensaje_documento.alpha = 0.8f
                }
                departamento.text.toString().isEmpty() -> {
                    mensaje_departamento.text="Ingresa un departamento"
                mensaje_departamento.alpha = 0.8f
                }
                emailInstitucional.text.toString().isEmpty() ->{
                    mensaje_emailInstitucional.text="Ingresa un email institucional"
                mensaje_emailInstitucional.alpha = 0.8f
                }
                emailPersonal.text.toString().isEmpty() ->{
                    mensaje_emailPersonal.text="Ingresa un email personal"
                mensaje_emailPersonal.alpha = 0.8f
                }
                emailPersonalConfirmar.text.toString().isEmpty() -> {
                    mensaje_emailPersonalConfirmar.text="Confirma tu email personal"
                mensaje_emailPersonalConfirmar.alpha = 0.8f
                }
                genero.text.toString().isEmpty() -> {
                    mensaje_genero.text="Ingresa un género"
                mensaje_genero.alpha = 0.8f
                }
                itr.text.toString().isEmpty() -> {
                    mensaje_itr.text="Ingresa un itr"
                mensaje_itr.alpha = 0.8f
                }
                localidad.text.toString().isEmpty() ->{
                    mensaje_localidad.text="Ingresa una localidad"
                mensaje_localidad.alpha = 0.8f
                }
                nombre.text.toString().isEmpty() ->{
                    mensaje_nombre.text="Ingresa un nombre"
                mensaje_nombre.alpha = 0.8f
                }
                nombreUsuario.text.toString().isEmpty() ->{
                    mensaje_nombreUsuario.text="Ingresa un nombre de usuario"
                mensaje_nombreUsuario.alpha = 0.8f
                }
                telefono.text.toString().isEmpty() -> {
                    mensaje_telefono.text = "Ingresa un teléfono"
                    mensaje_telefono.alpha = 0.8f
                }

            }

 */

        }
    }
}