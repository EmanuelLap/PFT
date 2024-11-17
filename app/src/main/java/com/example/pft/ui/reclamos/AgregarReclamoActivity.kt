package com.example.pft.ui.reclamos

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import com.example.pft.ApiClient
import com.example.pft.ApiService
import com.example.pft.R
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.Evento
import com.example.pft.entidades.ReclamoDTOMobile
import com.example.pft.ui.eventos.EventoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AgregarReclamoActivity : AppCompatActivity() {

    private lateinit var titulo: EditText
    private lateinit var detalle: EditText
    private lateinit var listaEventos: Spinner
    private lateinit var creditos: EditText
    private lateinit var fechaButton: Button
    private lateinit var fechaText: TextView
    private lateinit var semestre: Spinner
    private lateinit var agregarReclamo: FloatingActionButton
    private lateinit var mensaje:TextView
    private lateinit var volver: FloatingActionButton
    var eventoId=0

    //mensajes
    private lateinit var mensaje_titulo: TextView
    private lateinit var mensaje_detalle: TextView
    private lateinit var mensaje_creditos: TextView
    private lateinit var mensaje_fecha: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_reclamo)

        val locale = Locale("es", "ES")
        Locale.setDefault(locale)

        //Declaro rutas a elementos
        titulo=findViewById(R.id.agregarReclamoActivity_Titulo)
        detalle=findViewById(R.id.agregarReclamoActivity_Descripcion)
        listaEventos=findViewById(R.id.agregarReclamoActivity_evento)
        creditos=findViewById(R.id.agregarReclamoActivity_creditos)
        fechaButton = findViewById(R.id.agregarReclamoActivity_fecha)
        fechaText=findViewById(R.id.agregarReclamoActivity_fecha_seleccionada)
        semestre=findViewById(R.id.agregarReclamoActivity_semestre)
        agregarReclamo=findViewById(R.id.agregarReclamoActivity_agregar)
        volver=findViewById(R.id.agregarReclamoActivity_volver)
        mensaje=findViewById(R.id.agregarReclamoActivity_Mensaje)

        //mensajes
        mensaje_titulo=findViewById(R.id.agregarReclamoActivity_mensaje_titulo)
        mensaje_detalle=findViewById(R.id.agregarReclamoActivity_mensaje_descripcion)
        mensaje_creditos=findViewById(R.id.agregarReclamoActivity_mensaje_creditos)
        mensaje_fecha=findViewById(R.id.agregarReclamoActivity_mensaje_fecha)

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


        //Spinner eventos

        Log.d("AgregarReclamoActivity", "onCreateView")

       /* val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)*/
        val apiService = ApiClient.getApiService(this)
        val call = apiService.obtenerEventos()



        call.enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if (response.isSuccessful) {
                    val eventos = response.body() ?: emptyList()
                    Log.d("AgregarReclamoActivity", "API call successful. Eventos: $eventos")

                    // Configurar el ArrayAdapter
                    val eventosAdapter = EventoAdapter(this@AgregarReclamoActivity, eventos)

                    // Asignar el adapter al ListView
                    listaEventos.adapter = eventosAdapter

                    listaEventos.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parentView: AdapterView<*>,
                                selectedItemView: View?,
                                position: Int,
                                id: Long
                            )  {
                                // Verificar que la posición seleccionada esté dentro de los límites
                                if (position >= 0 && position < eventos.size) {
                                    // Obtiene el evento seleccionado
                                    val eventoSeleccionado = eventos[position]

                                    // Almacena el ID del evento seleccionado en la variable
                                    eventoId = eventoSeleccionado.id!!
                                } else {
                                    // Puedes manejar esta situación según tus necesidades
                                    Log.e("AgregarReclamoActivity", "Posición seleccionada fuera de los límites")
                                }
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>) {
                                // Manejar caso cuando no hay nada seleccionado (si es necesario)
                            }

                        }
                }else {
                    Log.e("EventoFragment", "API call failed with code ${response.code()}")
                    // Resto del código para manejar errores...
                }
            }

            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                Log.e("EventoFragment", "API call failed", t)
                // Resto del código para manejar errores...
            }
        })

        //---------------------------------------------------------------


        //Agregar Reclamo


        agregarReclamo.setOnClickListener {

            val camposVacios = mutableListOf<String>()

            if (titulo.text.toString().isEmpty()) {
                camposVacios.add("titulo")
                mensaje_titulo.text = "Ingresa un titulo"
                mensaje_titulo.alpha = 0.8f
                mensaje_titulo.visibility = View.VISIBLE
            } else {
                mensaje_titulo.visibility = View.INVISIBLE
            }

            if (detalle.text.toString().isEmpty()) {
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

                val formatoFecha = SimpleDateFormat("dd/mm/yyyy")

                val estudianteId = usuario?.id
                val tituloIngresado = titulo.text.toString()
                val detalleIngresado = detalle.text.toString()
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
                    eventoId,
                    fechaIngresada,
                    null,
                    semestreSeleccionado,
                    tituloIngresado
                )

                val responseJson= Gson().toJson(reclamo)
                Log.d("ReclamoActivity", "ResponseBody: $responseJson")

                Log.d("AgregarReclamoActivity", "eventoId: ${eventoId}")

                // Convertir la cadena de fecha a un objeto Date
                //    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                // val date: Date = dateFormat.parse(fechaIngresada.toString()) ?: Date()


                val apiService = ApiClient.getApiService(this)
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

                        mensaje.text = "Ocurrió un error al crear el reclamo"
                    }
                })

            }
        }

        fechaButton.setOnClickListener {
            mostrarCalendario()
        }

        volver.setOnClickListener{
           // val mainActivity = Intent(this@AgregarReclamoActivity, MainActivity::class.java)
          //  startActivity(mainActivity)
            finish()
        }

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
