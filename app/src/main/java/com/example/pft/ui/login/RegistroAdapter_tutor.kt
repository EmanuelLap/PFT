package com.example.pft.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.pft.R
import com.example.pft.entidades.AreaDTO
import com.example.pft.entidades.TipoTutorDTO

class RegistroAdapter_tutor(
    private val tiposTutor: List<TipoTutorDTO>,
    private val areas: List<AreaDTO>
) : RecyclerView.Adapter<RegistroAdapter_tutor.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tipoTutorSpinner: Spinner = itemView.findViewById(R.id.seccion_registro_tutor_rol)
        val areaSpinner: Spinner = itemView.findViewById(R.id.seccion_registro_tutor_area)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_seccion_registro_tutor, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tiposTutor.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val tipoTutor = tiposTutor[position]

        // Configurar el adaptador para el Spinner 'tipoTutorSpinner'
        val tipoTutorAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, listOf(tipoTutor.nombre))
        tipoTutorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.tipoTutorSpinner.adapter = tipoTutorAdapter

        // Configurar el adaptador para el Spinner 'areaSpinner'
        val areaAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, areas.map { it.nombre })
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.areaSpinner.adapter = areaAdapter
    }
}