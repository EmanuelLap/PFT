package com.example.pft.ui.login

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pft.entidades.Itr

class ItrAdapter(context: Context, itrs: List<Itr>) : ArrayAdapter<Itr>(context, android.R.layout.simple_spinner_item, itrs) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val itr = getItem(position)
        (view as TextView).text = itr?.nombre.toString()
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val itr = getItem(position)
        (view as TextView).text = itr?.nombre.toString()
        return view
    }
}