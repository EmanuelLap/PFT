package com.example.pft

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class EventoAdapter (private val lista: ArrayList<String>) : RecyclerView.Adapter<EventoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.seccion_evento_textView)
        val imageView: ImageView= itemView.findViewById(R.id.seccion_evento_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_seccion_evento, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.textView.text = item
        if (item == "Evento 1") {
            holder.imageView.setImageResource(R.mipmap.login)
        } else if(item=="Evento 2"){
            holder.imageView.setImageResource(R.mipmap.dia)
        }else if(item=="Evento 3"){
            holder.imageView.setImageResource(R.mipmap.tarde)
        }else if(item=="Evento 4"){
            holder.imageView.setImageResource(R.mipmap.noche)
        }
    }


    override fun getItemCount(): Int {
        return lista.size
    }
}