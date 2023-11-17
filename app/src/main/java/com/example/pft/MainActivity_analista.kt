package com.example.pft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pft.ui.eventos.Evento_analistaActivity
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

        val eventosActivity = Intent(this, Evento_analistaActivity::class.java)
        val reclamosActivity = Intent(this, Reclamo_analistaActivity::class.java)
        val usuariosActivity = Intent(this, Usuarios_AnalistaActivity::class.java)


        bottomNavigationView = findViewById(R.id.analistaBottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAnalistaUsuarios -> startActivity(usuariosActivity)
                R.id.menuAnalistaEventos -> startActivity(eventosActivity)
                R.id.menuAnalistaReclamos -> startActivity(reclamosActivity)
            }
            true
        }


    }
}



