package com.example.pft.ui.perfil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.pft.R
import com.example.pft.UsuarioSingleton


class PerfilFragment : Fragment() {

    private lateinit var nombre: TextView
    private lateinit var imagen_perfil: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        nombre=view.findViewById(R.id.perfilFragment_nombreApellido)
        imagen_perfil=view.findViewById(R.id.perfilFragment_imagen)

        // Recuperar el valor del "usuario"

        val usuario = UsuarioSingleton.usuario



        // Determinar qué imagen de perfil cargar según el género del usuario
        val imagenPerfil = if (usuario?.genero.toString() == "F") {
            R.drawable.drawable_perfil_femenino
        } else {
            R.drawable.drawable_perfil_masculino
        }

        // Establecer la imagen de perfil
        imagen_perfil.setImageResource(imagenPerfil)
        nombre.text=usuario?.nombres + "\n" + usuario?.apellidos
        // Inflate the layout for this fragment
        return view
    }


}