package com.example.pft.ui.reclamos

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
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
import java.util.Locale

private lateinit var titulo: TextView;
private lateinit var detalle: TextView;
private lateinit var estado: TextView;
private lateinit var respuesta: EditText
private lateinit var btn_actualizar: Button
private lateinit var spinnerEstado: Spinner
private lateinit var btn_volver: FloatingActionButton

class ReclamoAnalistaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclamo_analista)

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


        titulo=findViewById(R.id.reclamoSel_titulo)
        detalle=findViewById(R.id.reclamoSel_detalle)
        estado=findViewById(R.id.reclamoSel_estado)
        respuesta=findViewById(R.id.reclamoSel_respuesta)
        btn_actualizar=findViewById(R.id.reclamoSel_btnModificarReclamo)
        spinnerEstado=findViewById(R.id.reclamoSel_actEstado)
        btn_volver=findViewById(R.id.reclamoSel_btnVolver)

        titulo.text="Título: ${reclamoDTOSeleccionado.titulo}"
        detalle.text="Detalle: ${reclamoDTOSeleccionado.detalle}"
        val estadoTexto = if (reclamoDTOSeleccionado.activo == true) {
            "Estado: Esperando respuesta"
        } else {
            "Estado: Resuelto"
        }
        estado.text = estadoTexto


        btn_volver.setOnClickListener{
            finish()
        }


    }

    private fun mostrarMensajeExito() {
        runOnUiThread {
            val builder = AlertDialog.Builder(this@ReclamoAnalistaActivity)
            builder.setTitle("Éxito")
            builder.setMessage("Reclamo eliminado con éxito")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                val mainActivity = Intent(this@ReclamoAnalistaActivity, MainActivity::class.java)
                startActivity(mainActivity)
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}