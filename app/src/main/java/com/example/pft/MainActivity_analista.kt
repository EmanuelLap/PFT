package com.example.pft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.pft.ui.eventos.Evento_analistaActivity
import com.example.pft.ui.login.LoginActivity
import com.example.pft.ui.perfil.PerfilActivity
import com.example.pft.ui.reclamos.Reclamo_analistaActivity
import com.example.pft.ui.usuarios.Usuarios_AnalistaActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale


class MainActivity_analista : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_analista)

        val locale = Locale("es", "ES")
        Locale.setDefault(locale)


        val perfilActivity = Intent(this, PerfilActivity::class.java)
        val eventosActivity = Intent(this, Evento_analistaActivity::class.java)
        val reclamosActivity = Intent(this, Reclamo_analistaActivity::class.java)
        val usuariosActivity = Intent(this, Usuarios_AnalistaActivity::class.java)
        val loginActivity = Intent(this, LoginActivity::class.java)

        bottomNavigationView = findViewById(R.id.analistaBottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAnalistaPerfil -> startActivity(perfilActivity)
                R.id.menuAnalistaUsuarios -> startActivity(usuariosActivity)
                R.id.menuAnalistaEventos -> startActivity(eventosActivity)
              //  R.id.menuAnalistaReclamos -> startActivity(reclamosActivity)
              //  R.id.menuAnalistaCerrarSesión -> startActivity(loginActivity)
            }
            true
        }

        /*
        // Puedes agregar un ícono y hacer clic en él para mostrar el menú emergente
        val moreOptionsIcon = bottomNavigationView.findViewById<View>(R.id.menuAnalistaMasOpciones)
        moreOptionsIcon.setOnClickListener {
            showPopupMenu(moreOptionsIcon)
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_mas_opciones_analista, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuAnalistaReclamo -> {
                    // Abre la actividad de reclamos u realiza la acción deseada
                    true
                }
                // Agrega más opciones según sea necesario
                else -> false
            }
        }
        popupMenu.show()


         */
    }


}



