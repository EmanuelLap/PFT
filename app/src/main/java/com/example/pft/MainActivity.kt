package com.example.pft

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.NavigationUI
import com.example.pft.databinding.ActivityMainBinding
import com.example.pft.ui.eventos.EventoFragment
import com.example.pft.ui.login.LoginActivity
import com.example.pft.ui.perfil.PerfilFragment
import com.example.pft.ui.reclamos.ReclamoFragment
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locale = Locale("es", "ES")
        Locale.setDefault(locale)

        Log.d("MainActivity", "usuarioSingleton1: ${UsuarioSingleton.usuario}")
        val usuarioA = intent.getStringExtra("usuario")
        Log.d("MainActivity", "usuario: ${usuarioA}")

        Log.d("MainActivity", "usuarioSingleton2: ${UsuarioSingleton.usuario}")


       /* if(UsuarioSingleton.usuario==null) {
            val usuario = intent.getStringExtra("usuario")

            UsuarioSingleton.usuario = usuario
        }

        */

        val usuario = UsuarioSingleton.usuario



        Log.d("MainActivity", "usuario: ${usuario}")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        Log.d("MainActivity", "Valor de usuario antes de asignar al Bundle: $usuario")

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top-level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_perfil, R.id.nav_eventos_analista, R.id.nav_reclamos_estudiante, R.id.nav_cerrarSesion
            ), drawerLayout
        )

        val navMenu = navView.menu

        // Ocultar todos los grupos específicos inicialmente
        navMenu.setGroupVisible(R.id.group_analista_specific, false)
        navMenu.setGroupVisible(R.id.group_estudiante_specific, false)
        navMenu.setGroupVisible(R.id.group_tutor_specific, false)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        when (usuario?.rol!!.nombre) {
            "ANALISTA" -> {
                navMenu.setGroupVisible(R.id.group_analista_specific, true)
            }

            "ESTUDIANTE" -> {
                navMenu.setGroupVisible(R.id.group_estudiante_specific, true)
            }

            "TUTOR" -> {
                navMenu.setGroupVisible(R.id.group_tutor_specific, true)
            }
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    UsuarioSingleton.usuario=null
                    val login = Intent(this,LoginActivity::class.java)
                    startActivity(login)
                    true
                }

                R.id.nav_perfil -> {
                    navController.navigate(R.id.nav_perfil)
                    true
                }

                //ANALISTA
                R.id.nav_eventos_analista -> {
                    navController.navigate(R.id.nav_eventos_analista)
                   true
                }

                R.id.nav_reclamos_estudiante -> {
                    navController.navigate(R.id.nav_reclamos_estudiante)
                    true
                }

                //ESTUDIANTE
                R.id.nav_eventos_analista -> {
                    navController.navigate(R.id.nav_eventos_analista)
                    true
                }

                R.id.nav_reclamos_estudiante -> {
                    navController.navigate(R.id.nav_reclamos_estudiante)
                    true
                }

                else -> {
                    Log.d("MainActivity", "No funciona")

                    NavigationUI.onNavDestinationSelected(
                        menuItem,
                        navController
                    ) || super.onOptionsItemSelected(menuItem)

                    // Cierra el DrawerLayout después de seleccionar la opción
                    drawerLayout.closeDrawer(navView)
                    true
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}