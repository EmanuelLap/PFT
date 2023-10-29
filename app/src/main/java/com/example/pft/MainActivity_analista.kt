package com.example.pft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pft.databinding.ActivityMainAnalistaBinding
import com.example.pft.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import java.util.Locale

private lateinit var appBarConfiguration: AppBarConfiguration
private lateinit var binding: ActivityMainAnalistaBinding
class MainActivity_analista : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_analista)

        val locale = Locale("es", "ES")
        Locale.setDefault(locale)

        binding = ActivityMainAnalistaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMainAnalista.toolbarAnalista)

        val drawerLayout: DrawerLayout = binding.drawerLayoutAnalista

       val navView: NavigationView = binding.navViewAnalista
/*
        val navController = findNavController(R.id.nav_host_fragment_analista)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_perfil_analista, R.id.nav_eventos_analista, R.id.nav_reclamos_analista
            ), drawerLayout
        )


       setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_analista)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

     */
    }
}



