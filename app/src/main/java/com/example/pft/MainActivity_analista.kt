package com.example.pft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import com.example.pft.entidades.EstudianteId
import com.example.pft.ui.eventos.Evento_analistaActivity
import com.example.pft.ui.login.LoginActivity
import com.example.pft.ui.perfil.PerfilActivity
import com.example.pft.ui.reclamos.Reclamo_analistaActivity
import com.example.pft.ui.usuarios.Usuarios_AnalistaActivity
import com.google.gson.Gson
import java.util.Locale


class MainActivity_analista : AppCompatActivity() {

    private lateinit var nombre: TextView
    private lateinit var imagen_perfil: ImageView
    private lateinit var icon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_analista)


        nombre=findViewById(R.id.analista_nombreApellido)
        imagen_perfil=findViewById(R.id.analista_imagen)

        val locale = Locale("es", "ES")
        Locale.setDefault(locale)
/*
        if(UsuarioSingleton.usuario==null) {
            val usuario = intent.getStringExtra("usuario")

            UsuarioSingleton.usuario = usuario
            Log.d("AnalistaMain", "Usuario: $usuario")
        }

 */

        val usuarioJson=UsuarioSingleton.usuario
/*
        // Convertir la cadena JSON de vuelta a un objeto Usuario (usando Gson)
        val usuario = Gson().fromJson(usuarioJson, Usuario::class.java)
        Log.d("AnalistaMain", "Usuario: $usuario")
        // Determinar qué imagen de perfil cargar según el género del usuario
        val imagenPerfil = if (usuario.genero == "F") {
            R.drawable.drawable_perfil_femenino
        } else {
            R.drawable.drawable_perfil_masculino
        }

        // Establecer la imagen de perfil
        imagen_perfil.setImageResource(imagenPerfil)
        nombre.text=usuario.nombres + "\n" + usuario.apellidos




 */

        val toolbar: Toolbar = findViewById(R.id.analista_toolbar)
        setSupportActionBar(toolbar)

        // Encuentra el ícono en el menú inflado
        val menu = toolbar.menu
        val menuItem = menu.findItem(R.id.toolbar_icon)
        // Encuentra el ícono en la vista de acción de la barra de herramientas
        val ab = supportActionBar
        ab?.setDisplayShowCustomEnabled(true)
        ab?.setCustomView(R.layout.app_bar_main_analista) // Reemplaza con tu layout

        // Encuentra el ImageView en el layout personalizado de la barra de herramientas
        icon = ab?.customView?.findViewById(R.id.toolbar_icon) as ImageView

        icon.setOnClickListener {
            showPopupMenu(it)
        }

    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.main_analista_menu) // Infla el menú correcto
        popupMenu.show()

        val perfilActivity = Intent(this, PerfilActivity::class.java)
        val eventosActivity = Intent(this, Evento_analistaActivity::class.java)
        val reclamosActivity = Intent(this, Reclamo_analistaActivity::class.java)
        val usuariosActivity = Intent(this, Usuarios_AnalistaActivity::class.java)
        val loginActivity = Intent(this, LoginActivity::class.java)


        // clics en los elementos del menú
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuAnalistaPerfil -> startActivity(perfilActivity)
                R.id.menuAnalistaUsuarios -> startActivity(usuariosActivity)
                R.id.menuAnalistaEventos -> startActivity(eventosActivity)
                R.id.menuAnalistaReclamos -> startActivity(reclamosActivity)
                R.id.menuAnalistaCerrarSesion -> {UsuarioSingleton.usuario = null
                startActivity(loginActivity)}
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_analista_menu, menu)
        return true
    }

}



