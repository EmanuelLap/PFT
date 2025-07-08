package com.example.pft.ui.reclamos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.Spinner
import com.example.pft.ApiClient
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.ReclamoDTO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReclamoFragment : Fragment() {

    private lateinit var btn_agregar : FloatingActionButton
    private lateinit var listaReclamos: ListView
    private lateinit var usuario: Usuario
    private lateinit var reclamoDTOS: List<ReclamoDTO>
    private lateinit var estadoSpinner: Spinner
    private lateinit var usuarioSpinner: Spinner
    private lateinit var reclamosFiltrados: List<ReclamoDTO>
    private var reclamosUsuario: List<ReclamoDTO> = emptyList()
    private lateinit var limpiarFiltros: Button





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
        reclamoDTOS=ArrayList()
        estadoSpinner=view.findViewById(R.id.fragmentReclamo_estado)
        usuarioSpinner=view.findViewById(R.id.fragmentReclamo_usuario)
        reclamosFiltrados=ArrayList()
        limpiarFiltros=view.findViewById(R.id.fragmentReclamo_btnLimpiarFiltros)
        val layoutAnalista = view.findViewById<LinearLayout>(R.id.fragmentReclamo_analista_usuario)

        val estados = listOf("Ingresado", "En Proceso", "Finalizado")

        val adapterEstados = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            estados
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        estadoSpinner.adapter = adapterEstados





        val apiService = ApiClient.getApiService(requireContext())
        val call = apiService.obtenerReclamos()

        call.enqueue(object : Callback<List<ReclamoDTO>> {
            override fun onResponse(call: Call<List<ReclamoDTO>>, response: Response<List<ReclamoDTO>>) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        reclamoDTOS = response.body() ?: emptyList()
                        Log.d("ReclamoFragment", "API call successful. Reclamos: $reclamoDTOS")




                        //Filtramos Reclamos Activos
                        val reclamosActivos = reclamoDTOS.filter { it.activo==true }

                        //Filtramos Reclamos del usuario
                        reclamosUsuario=reclamosActivos.filter{ it.estudianteId.id==usuario.id}

                        if(usuario.rol.nombre=="ESTUDIANTE"){
                    //         reclamosUsuario = reclamosActivos.filter {it.estudianteDTO.id==usuario.id}

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

                            val reclamoDTOSeleccionado: ReclamoDTO = if (usuario.rol.nombre == "ESTUDIANTE") {
                                Log.d("ReclamoFragment", "reclamosUsuario: $reclamosUsuario")

                                reclamosUsuario[position]
                            } else {
                                reclamosActivos[position]
                            }
                            val reclamoJson = Gson().toJson(reclamoDTOSeleccionado)
                            val reclamoEstudianteActivity = Intent(requireContext(), ReclamoActivity::class.java)
                            val reclamoAnalistaActivity = Intent(requireContext(), ReclamoAnalistaActivity::class.java)

                            if(usuario.utipo=="ESTUDIANTE") {
                                reclamoEstudianteActivity.putExtra("reclamo", reclamoJson)
                                startActivity(reclamoEstudianteActivity)
                            } else {
                                reclamoAnalistaActivity.putExtra("reclamo", reclamoJson)
                                startActivity(reclamoAnalistaActivity)

                            }
                        }
                    } else {
                        Log.e("ReclamoFragment", "API call failed with code ${response.code()}")
                        // Resto del manejo de errores
                    }
                }
            }

            override fun onFailure(call: Call<List<ReclamoDTO>>, t: Throwable) {
                if (isAdded) {
                    Log.e("ReclamoFragment", "API call failed", t)
                }
            }
        })

        // Verificar el tipo de usuario y mostrar/ocultar contenido según corresponda
        if (usuario.rol.nombre == "ESTUDIANTE") {

            btn_agregar.visibility = View.VISIBLE
            layoutAnalista.visibility = View.GONE
        } else {
            btn_agregar.visibility = View.GONE
            layoutAnalista.visibility = View.VISIBLE
        }


        // Configuración del botón de agregar
        btn_agregar.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), btn_agregar)
            popupMenu.menuInflater.inflate(R.menu.reclamo_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
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

    fun actualizarListaReclamosPorEstado(activo: Boolean, reclamoDTOS: List<ReclamoDTO>) {
        val reclamosFiltrados = reclamoDTOS.filter { it.activo == activo }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            reclamosFiltrados?.map { "${it.titulo}\n${it.estudianteId.nombres} ${it.estudianteId.apellidos}\nSemestre ${it.semestre}"  } ?: emptyList()
        )
        listaReclamos.adapter = adapter
    }

}
