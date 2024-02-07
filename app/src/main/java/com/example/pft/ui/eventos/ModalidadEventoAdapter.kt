package com.example.pft.ui.eventos

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pft.entidades.ModalidadEvento

class ModalidadEventoAdapter (context: Context, modalidadesEvento: List<ModalidadEvento>) : ArrayAdapter<ModalidadEvento>(context, android.R.layout.simple_spinner_item, modalidadesEvento) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val modalidadEvento = getItem(position)
        (view as TextView).text = modalidadEvento?.nombre.toString()
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val modalidadEvento = getItem(position)
        (view as TextView).text = modalidadEvento?.nombre.toString()
        return view
    }
}