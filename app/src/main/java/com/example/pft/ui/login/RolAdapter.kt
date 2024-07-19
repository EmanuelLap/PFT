package com.example.pft.ui.login

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pft.entidades.Itr
import com.example.pft.entidades.Rol

class RolAdapter(context: Context, roles: List<Rol>) : ArrayAdapter<Rol>(context, android.R.layout.simple_spinner_item, roles) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val roles = getItem(position)
        (view as TextView).text = roles?.nombre.toString()
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val rol = getItem(position)
        (view as TextView).text = rol?.nombre.toString()
        return view
    }
}