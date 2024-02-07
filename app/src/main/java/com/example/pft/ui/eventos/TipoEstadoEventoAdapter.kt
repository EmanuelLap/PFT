package com.example.pft.ui.eventos

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pft.entidades.TipoEstadoEvento

class TipoEstadoEventoAdapter (context: Context, tipoEstadosEvento: List<TipoEstadoEvento>) : ArrayAdapter<TipoEstadoEvento>(context, android.R.layout.simple_spinner_item, tipoEstadosEvento) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val tipoEstadoEvento = getItem(position)
        (view as TextView).text = tipoEstadoEvento?.nombre.toString()
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val tipoEstadoEvento = getItem(position)
        (view as TextView).text = tipoEstadoEvento?.nombre.toString()
        return view
    }
}