package com.example.pft.ui.eventos

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pft.entidades.TipoEvento

class TipoEventoAdapter (context: Context, tiposEvento: List<TipoEvento>) : ArrayAdapter<TipoEvento>(context, android.R.layout.simple_spinner_item, tiposEvento) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val tipoEvento = getItem(position)
        (view as TextView).text = tipoEvento?.nombre.toString()
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val tipoEvento = getItem(position)
        (view as TextView).text = tipoEvento?.nombre.toString()
        return view
    }
}