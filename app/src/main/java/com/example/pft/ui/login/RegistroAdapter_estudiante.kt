package com.example.pft.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pft.EventoAdapter
import com.example.pft.R

class RegistroAdapter_estudiante : RecyclerView.Adapter<RegistroAdapter_estudiante.ViewHolder>() {

    // ViewHolder con los campos EditText
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val anoDeIngresoEditText: EditText = itemView.findViewById(R.id.seccion_registro_estudiante_anoDeIngreso)
        val generacionEditText: EditText = itemView.findViewById(R.id.seccion_registro_estudiante_generacion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_seccion_registro_estudiante, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    fun obtenerDatosRegistro(holder: ViewHolder): Pair<Int, Long> {
        // Obtener el texto de los campos de los EditText en el ViewHolder
        val generacion = holder.generacionEditText.text.toString().toInt()
        val anoDeIngreso = holder.anoDeIngresoEditText.text.toString().toLong()
        return Pair(generacion,anoDeIngreso)
    }
}