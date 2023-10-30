package com.example.pft.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RegistroAdapter_analista: RecyclerView.Adapter<RegistroAdapter_analista.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Puedes omitir esto ya que no se necesita en un RecyclerView vacío
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val emptyView = View(parent.context)
        return ViewHolder(emptyView)
    }

    override fun getItemCount(): Int {
        // Devuelve 0 para que el RecyclerView esté vacío
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // No es necesario hacer nada aquí en un RecyclerView vacío
    }
}