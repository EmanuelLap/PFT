package com.example.pft.ui.reclamos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import com.example.pft.ApiService
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.Evento
import com.example.pft.entidades.Reclamo
import com.example.pft.ui.eventos.EventoActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ReclamoFragment : Fragment() {

    private lateinit var btn_agregar : FloatingActionButton
    private lateinit var listaReclamos: ListView
    private lateinit var usuario: Usuario // Cambia el tipo según lo que sea UsuarioSingleton.usuario

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reclamo, container, false)

        // Obtener el usuario desde UsuarioSingleton
        usuario = UsuarioSingleton.usuario!!
        Log.d("ReclamoFragment", "Valor de usuario en el fragmento: $usuario")

        btn_agregar = view.findViewById(R.id.reclamos_agregar)
        listaReclamos = view.findViewById(R.id.reclamos_lista)

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")  // Reemplaza con tu URL base
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()
            )
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.obtenerReclamos()

        // Realizar la llamada asíncrona con Retrofit
        call.enqueue(object : Callback<List<Reclamo>> {
            override fun onResponse(call: Call<List<Reclamo>>, response: Response<List<Reclamo>>) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        val reclamos = response.body() ?: emptyList()
                        Log.d("ReclamoFragment", "API call successful. Reclamos: $reclamos")
                        val reclamosActivos = reclamos.filter { it.activo==true }

                        if(usuario.rol.nombre=="ESTUDIANTE"){
                            val reclamosUsuario = reclamosActivos.filter {it.estudianteId.id==usuario.id}

                            // Configurar el ArrayAdapter para estudiantes
                            val adapterEstudiante = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_list_item_1,
                                reclamosUsuario.map { it.titulo }
                            )

                            listaReclamos.adapter = adapterEstudiante
                        } else {
                            // Configurar el ArrayAdapter
                            val adapter = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_list_item_1,
                                reclamosActivos.map { it.titulo }
                            )

                            // Asignar el adapter al ListView
                            listaReclamos.adapter = adapter

                        }

                        // Al hacer clic en cualquier elemento de la lista
                        listaReclamos.setOnItemClickListener { _, _, position, _ ->
                            val reclamoSeleccionado = reclamos[position]
                            val reclamoJson = Gson().toJson(reclamoSeleccionado)
                            val intent = Intent(requireContext(), ReclamoActivity::class.java)
                            intent.putExtra("reclamo", reclamoJson)
                            startActivity(intent)
                        }
                    } else {
                        Log.e("ReclamoFragment", "API call failed with code ${response.code()}")
                        // Resto del manejo de errores
                    }
                }
            }

            override fun onFailure(call: Call<List<Reclamo>>, t: Throwable) {
                if (isAdded) {
                    Log.e("ReclamoFragment", "API call failed", t)
                    // Resto del manejo de errores
                }
            }
        })

        // Verificar el tipo de usuario y mostrar/ocultar el botón según corresponda
        if (usuario.rol.nombre == "ESTUDIANTE") {
            btn_agregar.visibility = View.VISIBLE
        } else {
            btn_agregar.visibility = View.GONE
        }
        // Configuración del botón de agregar
        btn_agregar.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), btn_agregar)
            popupMenu.menuInflater.inflate(R.menu.reclamo_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                val fragmentManager = requireActivity().supportFragmentManager
                when (item.itemId) {
                    R.id.opcion1 -> {
                        startActivity(Intent(requireContext(), AgregarReclamoActivity::class.java))
                        true
                    }
                    R.id.opcion2 -> {
                        val fragmentOpcion2 = AgregarReclamoVMEFragment()
                        replaceFragment(fragmentManager, R.id.nav_host_fragment_content_main, fragmentOpcion2)
                        true
                    }
                    R.id.opcion3 -> {
                        val fragmentOpcion3 = AgregarReclamoAPE_OPTFragment()
                        replaceFragment(fragmentManager, R.id.nav_host_fragment_content_main, fragmentOpcion3)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        return view
    }

    private fun replaceFragment(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
