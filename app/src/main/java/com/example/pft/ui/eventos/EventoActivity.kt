package com.example.pft.ui.eventos

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.pft.ApiClient
import com.example.pft.MainActivity
import com.example.pft.R
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.Evento
import com.example.pft.entidades.EventoDTOMobile
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class EventoActivity : AppCompatActivity() {

    private lateinit var titulo:TextView;
    private lateinit var tipo:TextView;
    private lateinit var modalidad:TextView;
    private lateinit var localizacion:TextView;
    private lateinit var inicio:TextView;
    private lateinit var fin:TextView;
    private lateinit var btn_asignarTutores:Button
    private lateinit var btn_eliminar:Button
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
        btn_asignarTutores=findViewById(R.id.evento_agregarTutores)
        btn_eliminar=findViewById(R.id.evento_eliminarEvento)

        // Configurar la visibilidad del layout basado en la condición del usuario
        if (UsuarioSingleton.usuario?.rol?.nombre == "ANALISTA") {
            btn_asignarTutores.visibility = View.VISIBLE
            btn_eliminar.visibility = View.VISIBLE
        } else {
            btn_asignarTutores.visibility = View.GONE
            btn_eliminar.visibility = View.GONE
        }

        // Paso timestamp de inicio y fin a fechas
        val timestampInicio = eventoSeleccionado.inicio
        val timestampFin = eventoSeleccionado.fin
        val fechaInicio = Date(timestampInicio)
        val fechaFin = Date(timestampFin)


        // Define el formato que deseas para la fecha
        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm")

    // Formatea la fecha a String
        val fechaInicioFormateada = formato.format(fechaInicio)
        val fechaFinFormateada = formato.format(fechaFin)

        titulo.text=eventoSeleccionado.titulo
        tipo.text="Tipo: ${eventoSeleccionado.tipoEvento.nombre}"
        modalidad.text="Modalidad: ${eventoSeleccionado.modalidadEvento.nombre}"
        localizacion.text="Localización: ${eventoSeleccionado.localizacion}"
        inicio.text="Inicio: $fechaInicioFormateada"
        fin.text="Fin: $fechaFinFormateada"

        //Datos del evento seleccionado

        val eventoSeleccionadoTitulo=eventoSeleccionado.titulo
        val eventoSeleccionadoId=eventoSeleccionado.id
        val eventoSeleccionadoTipo=eventoSeleccionado.tipoEvento
        val eventoSeleccionadoModalidad=eventoSeleccionado.modalidadEvento
        val eventoSeleccionadoInicio=eventoSeleccionado.inicio
        val eventoSeleccionadoFin=eventoSeleccionado.fin
        val eventoSeleccionadoTipoEstado=eventoSeleccionado.tipoEstadoEvento
        val eventoSeleccionadoTutores=eventoSeleccionado.tutorResponsableEventoDTOCollection
        val eventoSeleccionadoBaja=eventoSeleccionado.bajaLogica
        val eventoSeleccionadoItr=eventoSeleccionado.itrDTO
        val eventoSeleccionadoLocalizacion=eventoSeleccionado.localizacion


        val eventoSeleccioadotutores: List<Int> = eventoSeleccionadoTutores.map { it.id!! }

        val eventoMobile=EventoDTOMobile(eventoSeleccionadoBaja,eventoSeleccionadoFin,eventoSeleccionadoId,eventoSeleccionadoInicio,eventoSeleccionadoItr?.id!!,eventoSeleccionadoLocalizacion,eventoSeleccionadoModalidad.id,eventoSeleccionadoTipoEstado.id,eventoSeleccionadoTipo.id,eventoSeleccionadoTitulo,eventoSeleccioadotutores)

        btn_eliminar.setOnClickListener{

            val apiService = ApiClient.getApiService(this)
            val call = apiService.eliminarEvento(
                eventoMobile
            )

            Log.d("EventoActivity", "btnEliminar")

            call.enqueue(object : Callback<EventoDTOMobile> {
                override fun onResponse(call: Call<EventoDTOMobile>, response: Response<EventoDTOMobile>) {
                    if (response.isSuccessful) {
                        val eventoResp = response.body()
                        val responseJson = Gson().toJson(eventoResp)
                        Log.d("EventoActivity", "ResponseBody: $responseJson")
                        mostrarMensajeExito()
                    }
                }

                override fun onFailure(call: Call<EventoDTOMobile>, t: Throwable) {
                    Log.d("EventoActivity", "error: ${t}")

                }
            })
        }

        btn_volver.setOnClickListener{
            val mainActivity = Intent(this@EventoActivity, MainActivity::class.java)
            startActivity(mainActivity)
        }
    }

    private fun mostrarMensajeExito() {
        runOnUiThread {
            val builder = AlertDialog.Builder(this@EventoActivity)
            builder.setTitle("Éxito")
            builder.setMessage("Reclamo eliminado con éxito")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                val mainActivity = Intent(this@EventoActivity, MainActivity::class.java)
                startActivity(mainActivity)
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }


}