package com.example.pft.ui.usuarios

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import com.example.pft.ApiClient
import com.example.pft.ApiService
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.entidades.Itr
import com.example.pft.ui.login.ItrAdapter
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UsuariosFragment : Fragment() {

    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var documento: EditText
    private lateinit var itr: Spinner
    private lateinit var rol: Spinner
    private lateinit var listaUsuarios: ListView
    private lateinit var filtrar: Button
    private lateinit var limpiarFiltros: Button
    private lateinit var usuarios: List<Usuario>
    private lateinit var usuariosFiltrados: List<Usuario>


    private var itrSeleccionado: Itr? = null
    private lateinit var fragmentContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_usuarios, container, false)

        nombre = view.findViewById(R.id.usuariosFragment_nombre)
        apellido = view.findViewById(R.id.usuariosFragment_apellido)
        documento = view.findViewById(R.id.usuariosFragment_documento)
        itr = view.findViewById(R.id.usuariosFragment_itr)
        rol = view.findViewById(R.id.usuariosFragment_rol)
        listaUsuarios = view.findViewById(R.id.usuariosFragment_lista)
        filtrar = view.findViewById(R.id.usuariosFragment_btnFiltrar)
        limpiarFiltros = view.findViewById(R.id.usuariosFragment_btnLimpiarFiltros)
        usuarios = ArrayList()
        usuariosFiltrados= ArrayList()

       /* val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
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
       // val apiService: ApiService = ApiClient.getApiService(this)
        val apiService = ApiClient.getApiService(requireContext())
        val usuarioActivity = Intent(fragmentContext, ModificarUsuario_AnalistaActivity::class.java)

        val call = apiService.obtenerUsuarios()

        call.enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (isAdded()) {
                    if (response.isSuccessful) {
                        usuarios = response.body()!!
                        usuariosFiltrados=usuarios

                        val adapter = ArrayAdapter(
                            fragmentContext,
                            android.R.layout.simple_list_item_1,
                            usuarios.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}" }
                        )
                        listaUsuarios.adapter = adapter

                        listaUsuarios.setOnItemClickListener { _, _, i, _ ->
                            val usuarioSeleccionado = usuariosFiltrados[i]
                            val usuarioJson = Gson().toJson(usuarioSeleccionado)
                            usuarioActivity.putExtra("usuario", usuarioJson)
                            startActivity(usuarioActivity)
                        }
                    } else {
                        Log.e("API_CALL", "Error en la respuesta: ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                if (isAdded()) {
                    Log.e("UsuariosFragment", "API call failed", t)
                }
            }
        })

        val listaRol = listOf("ESTUDIANTE", "ANALISTA", "TUTOR")
        val rolAdapter = ArrayAdapter(fragmentContext, android.R.layout.simple_list_item_1, listaRol)
        rol.adapter = rolAdapter

        rol.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = listaRol[position]
                actualizarListaUsuariosPorRol(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones cuando no se ha seleccionado nada
            }
        }

        val callItr = apiService.obtenerITR()

        callItr.enqueue(object : Callback<List<Itr>> {
            override fun onResponse(call: Call<List<Itr>>, response: Response<List<Itr>>) {
                if (isAdded()) {
                    if (response.isSuccessful) {
                        val itrs = response.body() ?: emptyList()
                        val itrAdapter = ItrAdapter(fragmentContext, itrs)
                        itr.adapter = itrAdapter

                        itr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                if (position >= 0 && position < itrs.size) {
                                    itrSeleccionado = itrs[position]
                                    actualizarListaUsuariosPorItr(itrSeleccionado?.nombre.toString())
                                } else {
                                    Log.e("UsuariosFragment", "Posición seleccionada fuera de los límites")
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                // Acciones cuando no se ha seleccionado nada
                            }
                        }
                    } else {
                        Log.e("UsuariosFragment", "API call failed with code ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<Itr>>, t: Throwable) {
                if (isAdded()) {
                    Log.e("UsuariosFragment", "API call failed", t)
                }
            }
        })

        filtrar.setOnClickListener {
            when {
                nombre.text.isNotEmpty() && apellido.text.isEmpty() && documento.text.isEmpty() -> {
                    actualizarListaUsuariosPorNombre(nombre.text.toString())
                }
                apellido.text.isNotEmpty() && nombre.text.isEmpty() && documento.text.isEmpty() -> {
                    actualizarListaUsuariosPorApellido(apellido.text.toString())
                }
                nombre.text.isNotEmpty() && apellido.text.isNotEmpty() -> {
                    actualizarListaUsuariosPorNombreYApellido(nombre.text.toString(), apellido.text.toString())
                }
                documento.text.isNotEmpty() -> {
                    actualizarListaUsuariosPorDocumento(documento.text.toString())
                }
            }
        }

        limpiarFiltros.setOnClickListener{
            usuariosFiltrados = usuarios
            val adapter = ArrayAdapter(
                fragmentContext,
                android.R.layout.simple_list_item_1,
                usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}" }
            )

            listaUsuarios.adapter = adapter
        }

        return view
    }

    // Funciones de actualización del ListView

    private fun actualizarListaUsuariosPorDocumento(documento: String,) {
        usuariosFiltrados = usuarios.filter { it.documento.toString() == documento }
        val adapter = ArrayAdapter(
            fragmentContext,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}" }
        )

        listaUsuarios.adapter = adapter
    }

    private fun actualizarListaUsuariosPorNombre(nombre: String) {
        usuariosFiltrados = usuarios.filter { it.nombres == nombre }
        val adapter = ArrayAdapter(
            fragmentContext,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}" }
        )

        listaUsuarios.adapter = adapter
    }

    private fun actualizarListaUsuariosPorApellido(apellido: String) {
        usuariosFiltrados = usuarios.filter { it.apellidos == apellido }
        val adapter = ArrayAdapter(
            fragmentContext,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}" }
        )
        listaUsuarios.adapter = adapter
    }

    private fun actualizarListaUsuariosPorNombreYApellido(nombre: String, apellido: String) {
        val usuariosFiltradosPorNombre = usuarios.filter { it.nombres == nombre }
        usuariosFiltrados = usuariosFiltradosPorNombre.filter { it.apellidos == apellido }
        val adapter = ArrayAdapter(
            fragmentContext,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}" }
        )
        listaUsuarios.adapter = adapter
    }

    private fun actualizarListaUsuariosPorRol(rol: String) {
        usuariosFiltrados = usuarios.filter { it.rol.nombre == rol }
        val adapter = ArrayAdapter(
            fragmentContext,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}" }
        )
        listaUsuarios.adapter = adapter
    }

    private fun actualizarListaUsuariosPorItr(itr: String) {
        usuariosFiltrados = usuarios.filter { it.itr.nombre == itr }
        val adapter = ArrayAdapter(
            fragmentContext,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}"} ?: emptyList()
        )
        listaUsuarios.adapter = adapter
    }
}