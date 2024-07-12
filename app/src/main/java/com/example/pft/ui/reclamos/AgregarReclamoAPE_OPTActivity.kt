package com.example.pft.ui.reclamos

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pft.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar
import java.util.Locale

class AgregarReclamoAPE_OPTActivity : AppCompatActivity() {

        private lateinit var titulo: EditText
        private lateinit var descripcion: EditText
        private lateinit var nombre_actividad: EditText
        private lateinit var creditos: EditText
        private lateinit var fechaButton: Button
        private lateinit var fechaText: TextView
        private lateinit var semestre: Spinner
        private lateinit var docente: Spinner
        private lateinit var agregarReclamo: FloatingActionButton
        private lateinit var volver: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_reclamo_ape)

        val locale = Locale("es", "ES")
        Locale.setDefault(locale)

            //Declaro rutas a elementos
            titulo=findViewById(R.id.agregarReclamoAPE_OPT_Titulo)
            descripcion=findViewById(R.id.agregarReclamoAPE_OPT_Descripcion)
            nombre_actividad=findViewById(R.id.agregarReclamoAPE_OPT_actividad)
            creditos=findViewById(R.id.agregarReclamoAPE_OPT_creditos)
            fechaButton =findViewById(R.id.agregarReclamoAPE_OPT_fecha)
            fechaText=findViewById(R.id.agregarReclamoAPE_OPT_fecha_seleccionada)
            semestre=findViewById(R.id.agregarReclamoAPE_OPT_semestre)
            agregarReclamo=findViewById(R.id.agregarReclamoAPE_OPT_agregar)
            docente=findViewById(R.id.agregarReclamoAPE_OPT_docente)
            volver=findViewById(R.id.agregarReclamoAPE_OPT_volver)

            //spinner semestre
            // opciones (del 1 al 10)
            val semestreOpciones = ArrayList<String>()
            semestreOpciones.add("1")
            semestreOpciones.add("2")
            semestreOpciones.add("3")
            semestreOpciones.add("4")
            semestreOpciones.add("5")
            semestreOpciones.add("6")
            semestreOpciones.add("7")
            semestreOpciones.add("8")
            semestreOpciones.add("9")
            semestreOpciones.add("10")

            // Crear un ArrayAdapter y establecerlo en el Spinner
            val semestreAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, semestreOpciones)
            semestreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            semestre.adapter = semestreAdapter

            // Establecer el "hint" en el Spinner
            semestre.prompt = "Selecciona un semestre"

            //Spinner docente
            //opciones
            val docenteOpciones=ArrayList<String>()
            docenteOpciones.add("docente 1")
            docenteOpciones.add("docente 2")
            docenteOpciones.add("docente 3")
            docenteOpciones.add("docente 4")
            docenteOpciones.add("docente 5")

            val docenteAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, docenteOpciones)
            docenteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            docente.adapter = docenteAdapter

            // Establecer el "hint" en el Spinner
            semestre.prompt = "Selecciona un docente"


            fechaButton.setOnClickListener {
                mostrarCalendario()
            }

        volver.setOnClickListener{
            // val mainActivity = Intent(this@AgregarReclamoActivity, MainActivity::class.java)
            //  startActivity(mainActivity)
            finish()
        }

        }

        private fun mostrarCalendario() {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->

                    val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                    fechaText.text = fechaSeleccionada
                },
                year, month, day
            )
            datePickerDialog.show()
        }
    }