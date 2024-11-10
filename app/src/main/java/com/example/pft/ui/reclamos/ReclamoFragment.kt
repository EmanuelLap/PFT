package com.example.pft.ui.reclamos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.Spinner
import com.example.pft.ApiClient
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.Reclamo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReclamoFragment : Fragment() {

    private lateinit var btn_agregar : FloatingActionButton
    private lateinit var listaReclamos: ListView
    private lateinit var usuario: Usuario // Cambia el tipo según lo que sea UsuarioSingleton.usuario
    private lateinit var estado: Spinner
    private lateinit var reclamos: List<Reclamo>
    private var reclamosUsuario: List<Reclamo> = emptyList()




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
        estado = view.findViewById(R.id.reclamoAnalista_estado)
        reclamos=ArrayList()




        // Configurar Retrofit
      /*  val retrofit = Retrofit.Builder()
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

        val apiService = retrofit.create(ApiService::class.java)*/
        val apiService = ApiClient.getApiService(requireContext())
        val call = apiService.obtenerReclamos()

        // Realizar la llamada asíncrona con Retrofit
        call.enqueue(object : Callback<List<Reclamo>> {
            override fun onResponse(call: Call<List<Reclamo>>, response: Response<List<Reclamo>>) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        reclamos = response.body() ?: emptyList()
                        Log.d("ReclamoFragment", "API call successful. Reclamos: $reclamos")
                        val reclamosActivos = reclamos.filter { it.activo==true }

                        if(usuario.rol.nombre=="ESTUDIANTE"){
                             reclamosUsuario = reclamosActivos.filter {it.estudianteDTO.id==usuario.id}

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

                            val reclamoSeleccionado: Reclamo = if (usuario.rol.nombre == "ESTUDIANTE") {
                                reclamosUsuario[position]
                            } else {
                                reclamosActivos[position]
                            }
                            val reclamoJson = Gson().toJson(reclamoSeleccionado)
                            val reclamoEstudianteActivity = Intent(requireContext(), ReclamoActivity::class.java)
                            reclamoEstudianteActivity.putExtra("reclamo", reclamoJson)
                            startActivity(reclamoEstudianteActivity)
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

        val estadoAnalista = view.findViewById<LinearLayout>(R.id.reclamo_analista_estado)

        if (usuario.rol.nombre == "ANALISTA") {
            estadoAnalista.visibility = View.VISIBLE
        } else {
            estadoAnalista.visibility = View.GONE
        }

        //Filtros

        //spinner estado

        val estadoOpciones = ArrayList<String>()
        estadoOpciones.add("Ingresado")
        estadoOpciones.add("En proceso")
        estadoOpciones.add("Finalizado")


        // Crear un ArrayAdapter y establecerlo en el Spinner
        val estadoAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, estadoOpciones)
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        estado.adapter = estadoAdapter

        estado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                // Manejar la selección aquí
                val opcionSeleccionada = estadoOpciones[position]
                when (opcionSeleccionada) {
                    "Ingresado" -> {
                        actualizarListaReclamosPorEstado(true, reclamos)
                    }

                    "En proceso" -> {
                        actualizarListaReclamosPorEstado(true, reclamos)
                    }

                    "Finalizado" -> {
                        actualizarListaReclamosPorEstado(false, reclamos)
                    }
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Se llama cuando no se ha seleccionado nada
            }

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

                        startActivity(Intent(requireContext(), AgregarReclamoVMEActivity::class.java))
                        true
                    }
                    R.id.opcion3 -> {
                        startActivity(Intent(requireContext(), AgregarReclamoAPE_OPTActivity::class.java))
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        return view
    }

    fun actualizarListaReclamosPorEstado(activo: Boolean, reclamos: List<Reclamo>) {
        val reclamosFiltrados = reclamos.filter { it.activo == activo }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            reclamosFiltrados?.map { "${it.titulo}\n${it.estudianteDTO.nombres} ${it.estudianteDTO.apellidos}\nSemestre ${it.semestre}"  } ?: emptyList()
        )
        listaReclamos.adapter = adapter
    }

}
