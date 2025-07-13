package com.example.pft.ui.reclamos

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pft.ApiClient
import com.example.pft.ApiService
import com.example.pft.R
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.ReclamoDTOMobile
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AgregarReclamoVMEActivity : AppCompatActivity() {

    private lateinit var titulo: EditText
    private lateinit var descripcion: EditText
    private lateinit var nombre_actividad: EditText
    private lateinit var creditos: EditText
    private lateinit var empresa: EditText
    private lateinit var fechaButton: Button
    private lateinit var fechaText: TextView
    private lateinit var semestre: Spinner
    private lateinit var docente: Spinner
    private lateinit var agregarReclamo: FloatingActionButton
    private lateinit var volver: FloatingActionButton

    //mensajes
    private lateinit var mensaje_titulo: TextView
    private lateinit var mensaje_detalle: TextView
    private lateinit var mensaje_empresa: TextView
    private lateinit var mensaje_creditos: TextView
    private lateinit var mensaje_fecha: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_reclamo_vme)

        val locale = Locale("es", "ES")
        Locale.setDefault(locale)

        //Declaro rutas a elementos
        titulo=findViewById(R.id.agregarReclamoVME_Titulo)
        descripcion=findViewById(R.id.agregarReclamoVME_Descripcion)
        creditos=findViewById(R.id.agregarReclamoVME_creditos)
        fechaButton = findViewById(R.id.agregarReclamoVME_fecha)
        fechaText=findViewById(R.id.agregarReclamoVME_fecha_seleccionada)
        semestre=findViewById(R.id.agregarReclamoVme_semestre)
        agregarReclamo=findViewById(R.id.agregarReclamoVME_agregar)
        empresa=findViewById(R.id.agregarReclamoVME_VME)
        docente=findViewById(R.id.agregarReclamoVME_docente)
        volver=findViewById(R.id.agregarReclamoVME_volver)

        //mensajes
        mensaje_titulo=findViewById(R.id.agregarReclamoVMEActivity_mensaje_titulo)
        mensaje_detalle=findViewById(R.id.agregarReclamoVMEActivity_mensaje_descripcion)
        mensaje_creditos=findViewById(R.id.agregarReclamoVMEActivity_mensaje_creditos)
        mensaje_empresa=findViewById(R.id.agregarReclamoVMEActivity_mensaje_vme)
        mensaje_fecha=findViewById(R.id.agregarReclamoVMEActivity_mensaje_fecha)

        // Recuperar el valor del "usuario"

        val usuario = UsuarioSingleton.usuario

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

        agregarReclamo.setOnClickListener {

            val apiService = ApiClient.getApiService(this)
            val camposVacios = mutableListOf<String>()

            if (titulo.text.toString().isEmpty()) {
                camposVacios.add("titulo")
                mensaje_titulo.text = "Ingresa un titulo"
                mensaje_titulo.alpha = 0.8f
                mensaje_titulo.visibility = View.VISIBLE
            } else {
                mensaje_titulo.visibility = View.INVISIBLE
            }

            if (descripcion.text.toString().isEmpty()) {
                camposVacios.add("descripcion")
                mensaje_detalle.text = "Ingresa una descripción"
                mensaje_detalle.alpha = 0.8f
                mensaje_detalle.visibility = View.VISIBLE
            } else {
                mensaje_detalle.visibility = View.INVISIBLE
            }

            if (fechaText.text.toString().isEmpty()) {
                camposVacios.add("Fecha")
                mensaje_fecha.text = "Selecciona una fecha"
                mensaje_fecha.alpha = 0.8f
                mensaje_fecha.visibility = View.VISIBLE
            } else {
                mensaje_fecha.visibility = View.INVISIBLE
            }
            if (empresa.text.toString().isEmpty()) {
                camposVacios.add("actividad")
                mensaje_empresa.text = "Selecciona una actividad"
                mensaje_empresa.alpha = 0.8f
                mensaje_empresa.visibility = View.VISIBLE
            } else {
                mensaje_empresa.visibility = View.INVISIBLE
            }
            if (creditos.text.toString().isEmpty()) {
                camposVacios.add("creditos")
                mensaje_creditos.text = "Ingresa la cantidad de créditos"
                mensaje_creditos.alpha = 0.8f
                mensaje_creditos.visibility = View.VISIBLE
            } else {
                mensaje_creditos.visibility = View.INVISIBLE
            }

            if (camposVacios.isNotEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Todos los campos están completos

                val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                val estudianteId = usuario?.id
                val tituloIngresado = titulo.text.toString()
                val detalleIngresado = descripcion.text.toString()
                val creditosIngresados = creditos.text.toString().toInt()
                val semestreSeleccionado = semestre.selectedItem.toString().toInt()
                val fechaString = fechaText.text.toString()
                val fecha = formatoFecha.parse(fechaString)
                val fechaTimestamp = fecha.time
                val fechaIngresada = fechaTimestamp


                val reclamo = ReclamoDTOMobile(
                    true,
                    creditosIngresados,
                    detalleIngresado,
                    estudianteId!!,
                    1,
                    fechaIngresada,
                    null,
                    semestreSeleccionado,
                    tituloIngresado
                )

                Log.d("AgregarReclamoActivity", "eventoId: ${eventoId}")

                // Convertir la cadena de fecha a un objeto Date
                //    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                // val date: Date = dateFormat.parse(fechaIngresada.toString()) ?: Date()


                val call = apiService.agregarReclamo(
                    reclamo
                )

                call.enqueue(object : Callback<ReclamoDTOMobile> {
                    override fun onResponse(
                        call: Call<ReclamoDTOMobile>,
                        response: Response<ReclamoDTOMobile>
                    ) {
                        if (response.isSuccessful) {
                            val reclamoResp = response.body()
                            Log.d("AgregarReclamoActivity", "ResponseBody: $reclamoResp")
                            mostrarDialogoReclamoCreado()
                        }
                    }

                    override fun onFailure(call: Call<ReclamoDTOMobile>, t: Throwable) {
                        Log.d("AgregarReclamoActivity", "error: ${t}")
                    }
                })

            }
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

    private fun mostrarDialogoReclamoCreado() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Reclamo creado")
        builder.setMessage("Su reclamo ha sido creado correctamente.")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            // Al hacer clic en Aceptar, cierra la Activity
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }

}
