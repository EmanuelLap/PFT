package com.example.pft.ui.eventos

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pft.entidades.Evento

class EventoAdapter(context: Context, eventos: List<Evento>) : ArrayAdapter<Evento>(context, android.R.layout.simple_spinner_item, eventos) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val evento = getItem(position)
        (view as TextView).text = evento?.titulo.toString()
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val evento = getItem(position)
        (view as TextView).text = evento?.titulo.toString()
        return view
    }
}