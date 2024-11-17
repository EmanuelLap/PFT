package com.example.pft.ui.reclamos

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.pft.ApiClient
import com.example.pft.ApiService
import com.example.pft.MainActivity
import com.example.pft.R
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.Evento
import com.example.pft.entidades.ReclamoDTO
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
import java.util.Date

private lateinit var titulo: EditText;
private lateinit var detalle: EditText;
private lateinit var listaEventos: Spinner
private lateinit var creditos: EditText
private lateinit var fechaButton: Button
private lateinit var fechaText: TextView
private lateinit var semestre: Spinner
private lateinit var mensaje:TextView
private lateinit var btn_fecha:Button
private lateinit var btn_volver: FloatingActionButton;
private lateinit var btn_modificar: FloatingActionButton;

 var eventoId=0



class ModificarReclamoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_reclamo)

        titulo=findViewById(R.id.modificarReclamoActivity_Titulo)
        detalle=findViewById(R.id.modificarReclamoActivity_Descripcion)
        listaEventos=findViewById(R.id.modificarReclamoActivity_evento)
        creditos=findViewById(R.id.modificarReclamoActivity_creditos)
        fechaButton = findViewById(R.id.modificarReclamoActivity_fecha)
        fechaText=findViewById(R.id.modificarReclamoActivity_fecha_seleccionada)
        semestre=findViewById(R.id.modificarReclamoActivity_semestre)
        btn_modificar=findViewById(R.id.modificarReclamoActivity_modificar)
        btn_volver=findViewById(R.id.modificarReclamoActivity_volver)
        btn_fecha=findViewById(R.id.modificarReclamoActivity_fecha)
        mensaje=findViewById(R.id.modificarReclamoActivity_Mensaje)

        val reclamoJson = intent.getStringExtra("reclamo")



        //Consigo datos del reclamo

        val reclamoDTOSeleccionado = Gson().fromJson(reclamoJson, ReclamoDTO::class.java)

        eventoId=reclamoDTOSeleccionado.eventoId.id!!

        //Cargo Datos del reclamo
        val tituloEditable: Editable = Editable.Factory.getInstance().newEditable(reclamoDTOSeleccionado.titulo)
        val detalleEditable: Editable = Editable.Factory.getInstance().newEditable(reclamoDTOSeleccionado.detalle)
        val creditosEditable: Editable = Editable.Factory.getInstance().newEditable(reclamoDTOSeleccionado.creditos.toString())


        titulo.text=tituloEditable
        detalle.text=detalleEditable
        creditos.text=creditosEditable
        val fechaDate= Date(reclamoDTOSeleccionado.fecha)
        val formatoFecha=SimpleDateFormat("dd/mm/yyyy")
        val fechaConFormato=formatoFecha.format(fechaDate)

        fechaText.text=fechaConFormato.toString()


        // Recuperar el valor del "usuario"

        val usuario = UsuarioSingleton.usuario

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

        semestre.setSelection(reclamoDTOSeleccionado.semestre.toString().toInt()-1)

        // Establecer el "hint" en el Spinner
        semestre.prompt = "Selecciona un semestre"


        val apiService = ApiClient.getApiService(this)
        val call = apiService.obtenerEventos()

        Log.d("AgregarReclamoActivity", "Before API call, usuario: ${usuario}")

        call.enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if (response.isSuccessful) {
                    val eventos = response.body() ?: emptyList()
                    Log.d("AgregarReclamoActivity", "API call successful. Eventos: $eventos")

                    // Configurar el ArrayAdapter
                    val eventosAdapter = EventoAdapter(this@ModificarReclamoActivity, eventos)

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
                            }

                        }
                }else {
                    Log.e("EventoFragment", "API call failed with code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                Log.e("EventoFragment", "API call failed", t)
            }
        })




        btn_modificar.setOnClickListener{
            val reclamoTitulo=reclamoDTOSeleccionado.titulo
            val reclamoDetalle=reclamoDTOSeleccionado.detalle
            val reclamoCreditos=reclamoDTOSeleccionado.creditos.toInt()
            val reclamoEvento=reclamoDTOSeleccionado.eventoId
            val reclamoFecha=fechaConFormato.toString()
            val reclamoSemestre=reclamoDTOSeleccionado.semestre.toString().toInt()



            var tituloActual=titulo.text.toString()
            var detalleActual=detalle.text.toString()
            val creditosIngresados=creditos.text.toString().toInt()
            val semestreSeleccionado=semestre.selectedItem.toString().toInt()
            val fechaString=fechaText.text.toString()

            if (tituloActual!=reclamoTitulo || detalleActual!=reclamoDetalle||creditosIngresados!=reclamoCreditos||semestreSeleccionado!=reclamoSemestre||fechaString!=fechaConFormato){

                // Agrega un log para verificar que apiService se haya creado correctamente
                Log.d("ModificarReclamoActivity", "apiService creado correctamente")

                val formatoFecha= SimpleDateFormat("dd/mm/yyyy")
                val estudianteId=usuario?.id
                val tituloIngresado=titulo.text.toString()
                val detalleIngresado=detalle.text.toString()
                val creditosIngresados=creditos.text.toString().toInt()
                val semestreSeleccionado=semestre.selectedItem.toString().toInt()
                val fechaString=fechaText.text.toString()
                val fecha=formatoFecha.parse(fechaString)
                val fechaTimestamp=fecha.time
                val fechaIngresada=fechaTimestamp
                val id=reclamoDTOSeleccionado.id


                val reclamo=ReclamoDTOMobile(true,creditosIngresados,detalleIngresado,estudianteId!!, eventoId,fechaIngresada,id,semestreSeleccionado,tituloIngresado)

                Log.d("ModificarReclamoActivity", "eventoId: ${eventoId}")


                val apiService = ApiClient.getApiService(this)

                val call = apiService.modificarReclamo(
                    reclamo
                )

                call.enqueue(object : Callback<ReclamoDTOMobile> {
                    override fun onResponse(call: Call<ReclamoDTOMobile>, response: Response<ReclamoDTOMobile>) {
                        if (response.isSuccessful) {
                            val reclamoResp = response.body()
                            val responseJson = Gson().toJson(reclamoResp)
                            Log.d("AgregarReclamoActivity", "ResponseBody: $responseJson")
                            mostrarMensajeExito()
                        }
                    }

                    override fun onFailure(call: Call<ReclamoDTOMobile>, t: Throwable) {
                        Log.d("ModificarReclamoActivity", "error: ${t}")

                        mensaje.text="Ocurrió un error al modificar el reclamo"
                    }
                })


            } else{
                Toast.makeText(this, "Ningún dato se ha modificado.", Toast.LENGTH_SHORT).show()

            }
        }

        btn_volver.setOnClickListener{
            finish()
        }

        fechaButton.setOnClickListener {
            mostrarCalendario()
        }
    }

    private fun mostrarMensajeExito() {
        runOnUiThread {
            val builder = AlertDialog.Builder(this@ModificarReclamoActivity)
            builder.setTitle("Éxito")
            builder.setMessage("Reclamo modificado con éxito")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                val mainActivity = Intent(this@ModificarReclamoActivity, MainActivity::class.java)
                startActivity(mainActivity)
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
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