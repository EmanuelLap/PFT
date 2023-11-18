package com.example.pft.ui.eventos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.entidades.TutorId

class TutorAdapter(context: Context, tutorList: List<Usuario>) : ArrayAdapter<Usuario>(context, 0, tutorList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lista_agregar_tutor, parent, false)
        }

        val tutor = getItem(position)

        // Configurar el contenido del elemento de la lista
        val nombreTutorTextView: TextView = convertView!!.findViewById(R.id.nombreTutor)
        nombreTutorTextView.text = "${tutor?.nombres} ${tutor?.apellidos}"

        // Configurar el botón
        val agregarTutorButton: Button = convertView.findViewById(R.id.btnAgregarTutor)
        agregarTutorButton.setOnClickListener {
            // Aquí puedes manejar el evento de clic del botón
            // Puedes agregar la lógica para agregar el tutor, por ejemplo
            // o cualquier otra acción que desees realizar al hacer clic en el botón
            Toast.makeText(context, "Agregar tutor: ${tutor?.nombres}", Toast.LENGTH_SHORT).show()
        }

        return convertView
    }
}