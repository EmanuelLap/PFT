package com.example.pft.ui.reclamos

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pft.ApiClient
import com.example.pft.MainActivity
import com.example.pft.R
import com.example.pft.entidades.ReclamoDTO
import com.example.pft.entidades.ReclamoDTOMobile
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date
import java.util.Locale

private lateinit var titulo: TextView;
private lateinit var detalle: TextView;
private lateinit var fecha: TextView;
private lateinit var evento: TextView;
private lateinit var semestre: TextView;
private lateinit var creditos: TextView;
private lateinit var btn_modificar: Button
private lateinit var btn_eliminar: Button;
private lateinit var btn_volver: FloatingActionButton

class ReclamoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclamo)

        val locale = Locale("es", "ES")
        Locale.setDefault(locale)

        val reclamoJson = intent.getStringExtra("reclamo")

        // Convertir la cadena JSON de vuelta a un objeto Evento (usando Gson)
        val reclamoDTOSeleccionado = Gson().fromJson(reclamoJson, ReclamoDTO::class.java)

        val reclamoCreditos=reclamoDTOSeleccionado.creditos
        val reclamoDetalle=reclamoDTOSeleccionado.detalle
        val reclamoEstudianteId=reclamoDTOSeleccionado.estudianteId.id!!
        val reclamoEventoId=reclamoDTOSeleccionado.eventoId.id
        val reclamoFecha=reclamoDTOSeleccionado.fecha
        val reclamoId=reclamoDTOSeleccionado.id
        val reclamoSemestre=reclamoDTOSeleccionado.semestre
        val reclamoTitulo=reclamoDTOSeleccionado.titulo
        val reclamoMobile= ReclamoDTOMobile(true,reclamoCreditos,reclamoDetalle,reclamoEstudianteId,reclamoEventoId!!,reclamoFecha,reclamoId,reclamoSemestre,reclamoTitulo)


        titulo=findViewById(R.id.reclamoAct_titulo)
        detalle=findViewById(R.id.reclamoAct_detalle)
        fecha=findViewById(R.id.reclamoAct_fecha)
        evento=findViewById(R.id.reclamoAct_evento)
        semestre=findViewById(R.id.reclamoAct_semestre)
        creditos=findViewById(R.id.reclamoAct_creditos)
        btn_modificar=findViewById(R.id.reclamoAct_modificarReclamo)
        btn_eliminar=findViewById(R.id.reclamoAct_eliminarReclamo)
        btn_volver=findViewById(R.id.reclamoAct_btnVolver)

        titulo.text="Título: ${reclamoDTOSeleccionado.titulo}"
        detalle.text="Detalle: ${reclamoDTOSeleccionado.detalle}"
        val fechaString = Date(reclamoDTOSeleccionado.fecha).toString()
        fecha.text="Fecha: ${fechaString}"
        evento.text="Evento: ${reclamoDTOSeleccionado.eventoId.titulo}"
        semestre.text="Semestre: ${reclamoDTOSeleccionado.semestre}"
        creditos.text="Créditos: ${reclamoDTOSeleccionado.creditos}"


        btn_modificar.setOnClickListener{
            val modificarActivity = Intent(this@ReclamoActivity, ModificarReclamoActivity::class.java)
            modificarActivity.putExtra("reclamo", reclamoJson)
            startActivity(modificarActivity)
        }

        btn_eliminar.setOnClickListener{

            val apiService = ApiClient.getApiService(this)
            val call = apiService.eliminarReclamo(
                reclamoMobile
            )

            call.enqueue(object : Callback<ReclamoDTOMobile> {
                override fun onResponse(call: Call<ReclamoDTOMobile>, response: Response<ReclamoDTOMobile>) {
                    if (response.isSuccessful) {
                        val reclamoResp = response.body()
                        val responseJson = Gson().toJson(reclamoResp)
                        Log.d("ReclamoActivity", "ResponseBody: $responseJson")
                        mostrarMensajeExito()
                    }
                }

                override fun onFailure(call: Call<ReclamoDTOMobile>, t: Throwable) {
                    Log.d("ReclamoActivity", "error: ${t}")

                }
            })
        }

        btn_volver.setOnClickListener{
            finish()
        }


    }

    private fun mostrarMensajeExito() {
        runOnUiThread {
            val builder = AlertDialog.Builder(this@ReclamoActivity)
            builder.setTitle("Éxito")
            builder.setMessage("Reclamo eliminado con éxito")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                val mainActivity = Intent(this@ReclamoActivity, MainActivity::class.java)
                startActivity(mainActivity)
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}