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

class RegistroAdapter_estudiante(): RecyclerView.Adapter<RegistroAdapter_estudiante.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingreso: EditText = itemView.findViewById(R.id.seccion_registro_estudiante_anoDeIngreso)
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


}