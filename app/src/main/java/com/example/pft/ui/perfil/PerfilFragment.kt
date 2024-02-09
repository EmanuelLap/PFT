package com.example.pft.ui.perfil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.pft.R
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.EstudianteId
import com.google.gson.Gson


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

        val usuarioJson = UsuarioSingleton.usuario

        // Convertir la cadena JSON de vuelta a un objeto Usuario (usando Gson)
        val usuario = Gson().fromJson(usuarioJson, EstudianteId::class.java)

        // Determinar qué imagen de perfil cargar según el género del usuario
        val imagenPerfil = if (usuario.genero == "F") {
            R.drawable.drawable_perfil_femenino
        } else {
            R.drawable.drawable_perfil_masculino
        }

        // Establecer la imagen de perfil
        imagen_perfil.setImageResource(imagenPerfil)
        nombre.text=usuario.nombres + "\n" + usuario.apellidos
        // Inflate the layout for this fragment
        return view
    }


}