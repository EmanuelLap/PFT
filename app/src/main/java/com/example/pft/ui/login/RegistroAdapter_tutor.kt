package com.example.pft.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.pft.R

class RegistroAdapter_tutor (): RecyclerView.Adapter<RegistroAdapter_tutor.ViewHolder>() {

    private val areas = arrayOf("Área 1", "Área 2", "Área 3")
    private val roles = arrayOf("Rol 1", "Rol 2", "Rol 3")

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val area: Spinner = itemView.findViewById(R.id.seccion_registro_tutor_area)
        val rol: Spinner = itemView.findViewById(R.id.seccion_registro_tutor_rol)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_seccion_registro_tutor, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 1
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context

        // Crear y establecer un adaptador para el Spinner 'area'
        val areaAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, areas)
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.area.adapter = areaAdapter

        // Crear y establecer un adaptador para el Spinner 'rol'
        val rolAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, roles)
        rolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.rol.adapter = rolAdapter
    }
}