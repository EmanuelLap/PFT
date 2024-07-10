package com.example.pft.ui.usuarios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.pft.ApiService
import com.example.pft.MainActivity
import com.example.pft.MainActivity_analista
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.entidades.Evento
import com.example.pft.entidades.Itr
import com.example.pft.ui.eventos.EventoActivity
import com.example.pft.ui.login.ItrAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Usuarios_AnalistaActivity : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var documento: EditText
    private lateinit var itr: Spinner
    private lateinit var rol: Spinner
    private lateinit var listaUsuarios: ListView
    private lateinit var filtrar: Button
    private lateinit var volver: FloatingActionButton
    private lateinit var usuarios: List<Usuario>

    private var itrSeleccionado: Itr? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios_analista)

        nombre=findViewById(R.id.Usuarios_AnalistaActivity_nombre)
        apellido=findViewById(R.id.Usuarios_AnalistaActivity_apellido)
        documento=findViewById(R.id.Usuarios_AnalistaActivity_documento)
        itr=findViewById(R.id.Usuarios_AnalistaActivity_itr)
        rol=findViewById(R.id.Usuarios_AnalistaActivity_rol)
        listaUsuarios=findViewById(R.id.Usuarios_AnalistaActivity_lista)
        filtrar=findViewById(R.id.Usuarios_AnalistaActivity_btnFiltrar)
        volver=findViewById(R.id.Usuarios_AnalistaActivity_volver)
        usuarios=ArrayList()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
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

        val call=apiService.obtenerUsuarios()

        val usuarioActivity = Intent(this, ModificarUsuario_AnalistaActivity::class.java)


        call.enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    usuarios = response.body()!!

                    // Configurar el ArrayAdapter
                    val adapter = ArrayAdapter(
                        this@Usuarios_AnalistaActivity,
                        android.R.layout.simple_list_item_1,
                        usuarios?.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}"  } ?: emptyList()
                    )

                    // Asignar el adapter al ListView
                    listaUsuarios.adapter = adapter

                    // Al realizar click en cualquier elemento de la lista
                    listaUsuarios.setOnItemClickListener { adapterView, view, i, l ->
                        val usuarioSeleccionado = usuarios!!.get(i)

                        // Convierte el objeto Evento a una cadena JSON (por ejemplo, utilizando Gson)
                        val usuarioJson = Gson().toJson(usuarioSeleccionado)

                        // Crea un Intent y agrega la cadena JSON como extra
                        usuarioActivity.putExtra("usuario", usuarioJson)

                        // Iniciar la actividad con el Intent configurado
                        startActivity(usuarioActivity)
                    }
                }


                else {
                    // Manejar respuesta no exitosa
                    Log.e("API_CALL", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                Log.e("UsuariosAnalistaActivity", "API call failed", t)
            }
        })

        val listaRol = ArrayList<String>()
        // Agrego elementos
        listaRol.add("ESTUDIANTE")
        listaRol.add("ANALISTA")
        listaRol.add("TUTOR")

        val rolAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,listaRol)
        rol.adapter=rolAdapter

        // En tu método onCreate o donde configuras el Spinner y la lista de eventos
        rol.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = listaRol[position]
                when (selectedItem) {
                    "ESTUDIANTE" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaUsuariosPorRol("ESTUDIANTE", usuarios)
                    }

                    "ANALISTA" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaUsuariosPorRol("ANALISTA", usuarios)
                    }

                    "TUTOR" -> {
                        // Llamar a la función para filtrar y actualizar la lista de eventos
                        actualizarListaUsuariosPorRol("TUTOR", usuarios)
                    }

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Acciones cuando no se ha seleccionado nada
            }
        }

        val callItr = apiService.obtenerITR()


        callItr.enqueue(object : Callback<List<Itr>> {
            override fun onResponse(call: Call<List<Itr>>, response: Response<List<Itr>>) {
                if (response.isSuccessful) {
                    val itrs = response.body() ?: emptyList()
                    Log.d("AgregarReclamoActivity", "API call successful. Itrs: $itrs")

                    // Configurar el ArrayAdapter
                    val itrAdapter = ItrAdapter(
                        this@Usuarios_AnalistaActivity,
                        itrs
                    )

                    // Asignar el adapter al ListView
                    itr.adapter = itrAdapter

                    itr.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parentView: AdapterView<*>,
                                selectedItemView: View?,
                                position: Int,
                                id: Long
                            )  {
                                // Verificar que la posición seleccionada esté dentro de los límites
                                if (position >= 0 && position < itrs.size) {
                                    // Obtiene el itr seleccionado
                                    itrSeleccionado = itrs[position]

                                    actualizarListaUsuariosPorItr(itrSeleccionado!!.nombre.toString(), usuarios)

                                } else {
                                    // Puedes manejar esta situación según tus necesidades
                                    Log.e("Usuario_AnalistaActivity", "Posición seleccionada fuera de los límites")
                                }
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>) {
                                // Manejar caso cuando no hay nada seleccionado (si es necesario)
                            }

                        }
                }else {
                    Log.e("Usuario_AnalistaActivity", "API call failed with code ${response.code()}")
                    // Resto del código para manejar errores...
                }
            }

            override fun onFailure(call: Call<List<Itr>>, t: Throwable) {
                Log.e("ItrActivity", "API call failed", t)
                // Resto del código para manejar errores...
            }
        })

        volver.setOnClickListener{
            val mainActivity = Intent(this, MainActivity_analista::class.java)
            startActivity(mainActivity)
        }

        filtrar.setOnClickListener({
            if(nombre.text.isNotEmpty()&& apellido.text.isEmpty()&&documento.text.isEmpty()){
                actualizarListaUsuariosPorNombre(nombre.text.toString(),usuarios)
            }

            if(apellido.text.isNotEmpty() && nombre.text.isEmpty()&&documento.text.isEmpty()){
                actualizarListaUsuariosPorApellido(apellido.text.toString(),usuarios)
            }

            if(nombre.text.isNotEmpty()&& apellido.text.isNotEmpty()){
                actualizarListaUsuariosPorNombreYApellido(nombre.text.toString(),apellido.text.toString(),usuarios)
            }

            if(documento.text.isNotEmpty()){
                actualizarListaUsuariosPorDocumento(documento.text.toString(),usuarios)
            }
        })

    }
    // Esta función filtra los usuarios por documento y actualiza el adaptador del ListView

    fun actualizarListaUsuariosPorDocumento(documento: String, usuarios: List<Usuario>) {
        val usuariosFiltrados = usuarios.filter { it.documento.toString() == documento }

        val adapter = ArrayAdapter(
            this@Usuarios_AnalistaActivity,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}"} ?: emptyList()
        )
        listaUsuarios.adapter = adapter
    }
    // Esta función filtra los usuarios por nombre y actualiza el adaptador del ListView

    fun actualizarListaUsuariosPorNombre(nombre: String, usuarios: List<Usuario>) {
        val usuariosFiltrados = usuarios.filter { it.nombres == nombre }

        val adapter = ArrayAdapter(
            this@Usuarios_AnalistaActivity,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}"} ?: emptyList()
        )
        listaUsuarios.adapter = adapter
    }

    // Esta función filtra los usuarios por nombre y actualiza el adaptador del ListView

    fun actualizarListaUsuariosPorApellido(apellido: String, usuarios: List<Usuario>) {
        val usuariosFiltrados = usuarios.filter { it.apellidos == apellido }

        val adapter = ArrayAdapter(
            this@Usuarios_AnalistaActivity,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}"} ?: emptyList()
        )
        listaUsuarios.adapter = adapter
    }

    // Esta función filtra los usuarios por nombre y apellido y actualiza el adaptador del ListView

    fun actualizarListaUsuariosPorNombreYApellido(nombre: String, apellido: String, usuarios: List<Usuario>) {
        val usuariosFiltradosPorNombre = usuarios.filter { it.nombres == nombre }
        val usuariosFiltrados = usuariosFiltradosPorNombre.filter { it.apellidos == apellido }


        val adapter = ArrayAdapter(
            this@Usuarios_AnalistaActivity,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}"} ?: emptyList()
        )
        listaUsuarios.adapter = adapter
    }

    // Esta función filtra los usuarios por rol y actualiza el adaptador del ListView
    fun actualizarListaUsuariosPorRol(rol: String, usuarios: List<Usuario>) {
        val usuariosFiltrados = usuarios.filter { it.rol.nombre == rol }

        val adapter = ArrayAdapter(
            this@Usuarios_AnalistaActivity,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}"} ?: emptyList()
        )
        listaUsuarios.adapter = adapter
    }

    // Esta función filtra los usuarios por itr y actualiza el adaptador del ListView

    fun actualizarListaUsuariosPorItr(itr: String, usuarios: List<Usuario>) {
        val usuariosFiltrados = usuarios.filter { it.itr.nombre == itr }

        val adapter = ArrayAdapter(
            this@Usuarios_AnalistaActivity,
            android.R.layout.simple_list_item_1,
            usuariosFiltrados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}"} ?: emptyList()
        )
        listaUsuarios.adapter = adapter
    }
}