package com.example.pft.ui.eventos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pft.EventoAdapter
import com.example.pft.R


class EventoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_evento, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)



        // Configurar el RecyclerView con un GridLayoutManager
        val columnCount = 2 // Cambia esto al número deseado de columnas
        val layoutManager = GridLayoutManager(context, columnCount)
        recyclerView.layoutManager = layoutManager

        // Crear una lista de datos o modelos que deseas mostrar en las secciones
        val lista = ArrayList<String>()
        // Agregar elementos a listaDeDatos aquí
        lista.add("Evento 1")
        lista.add("Evento 2")
        lista.add("Evento 3")
        lista.add("Evento 4")
        // Crear un adaptador personalizado y asignarlo al RecyclerView
        val adapter = EventoAdapter(lista)

        recyclerView.adapter = adapter

        return view
    }
}
