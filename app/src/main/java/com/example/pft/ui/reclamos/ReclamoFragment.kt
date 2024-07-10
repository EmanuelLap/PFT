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

    final lateinit var btn_agregar : FloatingActionButton
    private lateinit var listaReclamos:ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_reclamo, container, false)

        Log.d("ReclamoFragment", "onCreateView")

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")  // Reemplaza "tu_direccion_ip" con la dirección IP de tu máquina de desarrollo
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Tiempo máximo de conexión
                    .readTimeout(30, TimeUnit.SECONDS)    // Tiempo máximo de lectura
                    .writeTimeout(30, TimeUnit.SECONDS)   // Tiempo máximo de escritura
                    .build()
            )
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.obtenerReclamos()

        val reclamo = Intent(requireContext(),ReclamoActivity::class.java)

        val agregarReclamo = Intent(requireContext(),AgregarReclamoActivity::class.java)

        // Recuperar el valor del "usuario"

        val usuario = UsuarioSingleton.usuario

        Log.d("ReclamoFragment", "Valor de usuario en el fragmento: $usuario")

        Log.d("ReclamoFragment", "usuario: ${usuario}")



        btn_agregar = view.findViewById(R.id.reclamos_agregar)
        listaReclamos=view.findViewById(R.id.reclamos_lista)

        // val reclamo = Intent(requireContext(), ReclamoActivity::class.java)

        Log.d("ReclamoFragment", "Before API call")

        call.enqueue(object : Callback<List<Reclamo>> {
            override fun onResponse(call: Call<List<Reclamo>>, response: Response<List<Reclamo>>) {
                if (response.isSuccessful) {
                    val reclamos = response.body()
                    Log.d("ReclamoFragment", "API call successful. Reclamos: $reclamos")

                    // Configurar el ArrayAdapter
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        reclamos?.map { "${it.titulo}" } ?: emptyList()
                    )

                    // Asignar el adapter al ListView
                    listaReclamos.adapter = adapter


                    // Al realizar click en cualquier elemento de la lista
                    listaReclamos.setOnItemClickListener { adapterView, view, i, l ->
                        val reclamoSeleccionado = reclamos!!.get(i)

                        // Convierte el objeto Evento a una cadena JSON (por ejemplo, utilizando Gson)
                        val reclamoJson = Gson().toJson(reclamoSeleccionado)

                        // Crea un Intent y agrega la cadena JSON como extra
                        reclamo.putExtra("reclamo", reclamoJson)

                        // Iniciar la actividad con el Intent configurado
                        startActivity(reclamo)
                    }
                }
                else {
                    Log.e("ReclamoFragment", "API call failed with code ${response.code()}")
                    // Resto del código para manejar errores...
                }
            }
            override fun onFailure(call: Call<List<Reclamo>>, t: Throwable) {
                Log.e("ReclamoFragment", "API call failed", t)
                // Resto del código para manejar errores...
            }
        })

        btn_agregar.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), btn_agregar)
            popupMenu.menuInflater.inflate(R.menu.reclamo_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->

                val fragmentManager = requireActivity().supportFragmentManager

                when (item.itemId) {
                    R.id.opcion1 -> {
                        agregarReclamo.putExtra("usuario", usuario)
                        startActivity(agregarReclamo)
                        true
                    }
                    R.id.opcion2 -> {
                        val fragmentOpcion2 = AgregarReclamoVMEFragment()
                        replaceFragment(fragmentManager,R.id.nav_host_fragment_content_main, fragmentOpcion2)
                        true
                    }
                    R.id.opcion3 -> {
                        val fragmentOpcion3 = AgregarReclamoAPE_OPTFragment()
                        replaceFragment(fragmentManager,
                            R.id.nav_host_fragment_content_main, fragmentOpcion3)
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

