package com.example.pft.ui.login

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pft.ApiClient
import com.example.pft.ApiService
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.entidades.AreaDTO
import com.example.pft.entidades.EstudianteDTO
import com.example.pft.entidades.Funcionalidades
import com.example.pft.entidades.Itr
import com.example.pft.entidades.Rol
import com.example.pft.entidades.TipoAreaDTO
import com.example.pft.entidades.TipoTutorDTO
import com.example.pft.entidades.TutorDTO
import com.example.pft.entidades.UsuarioDTO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegistroActivity : AppCompatActivity() {

    //botones
    private lateinit var btnVolver: FloatingActionButton
    private lateinit var btnConfirmar: FloatingActionButton

    //datos
    private lateinit var documento: EditText
    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var nombreUsuario: EditText
    private lateinit var contrasena: EditText
    private lateinit var contrasenaConfirmar: EditText
    private lateinit var emailInstitucional: EditText
    private lateinit var emailPersonal: EditText
    private lateinit var emailPersonalConfirmar: EditText
    private lateinit var telefono: EditText
    private lateinit var genero: Spinner
    private lateinit var generacion: EditText
    private lateinit var fecNac: Button
    private lateinit var itr: Spinner
    private lateinit var departamento: Spinner
    private lateinit var localidad: EditText
    private lateinit var tipoUsuario: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var fechaText: TextView

    private var itrSeleccionado: Itr? = null
    private var rolSeleccionado: Rol? = null


    //mensajes
    private lateinit var mensaje_documento: TextView
    private lateinit var mensaje_nombre: TextView
    private lateinit var mensaje_apellido: TextView
    private lateinit var mensaje_nombreUsuario: TextView
    private lateinit var mensaje_contrasena: TextView
    private lateinit var mensaje_contrasenaConfirmar: TextView
    private lateinit var mensaje_emailInstitucional: TextView
    private lateinit var mensaje_emailPersonal: TextView
    private lateinit var mensaje_emailPersonalConfirmar: TextView
    private lateinit var mensaje_telefono: TextView
    private lateinit var mensaje_genero: TextView
    private lateinit var mensaje_fecNac: TextView
    private lateinit var mensaje_itr: TextView
    private lateinit var mensaje_departamento: TextView
    private lateinit var mensaje_localidad: TextView
    private lateinit var mensaje_tipoUsuario: TextView
    private lateinit var registro_mensaje: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val locale = Locale("es", "ES")
        Locale.setDefault(locale)

        //Declaro rutas
        btnVolver = findViewById(R.id.registro_btnVolver)
        btnConfirmar = findViewById(R.id.registro_btnConfirmar)
        documento = findViewById(R.id.registro_documento)
        nombre = findViewById(R.id.registro_nombre)
        apellido = findViewById(R.id.registro_apellido)
        nombreUsuario = findViewById(R.id.registro_nombre_usuario)
        contrasena = findViewById(R.id.registro_contrasena)
        contrasenaConfirmar = findViewById(R.id.registro_confirmar_contrasena)
        emailInstitucional = findViewById(R.id.registro_email_institucional)
        emailPersonal = findViewById(R.id.registro_email_personal)
        emailPersonalConfirmar = findViewById(R.id.registro_confirmar_email_personal)
        telefono = findViewById(R.id.registro_telefono)
        genero = findViewById(R.id.registro_genero)
        fecNac = findViewById(R.id.registro_fec_nac)
        itr = findViewById(R.id.registro_itr)
        departamento = findViewById(R.id.registro_departamento)
        localidad = findViewById(R.id.registro_localidad)
        tipoUsuario = findViewById(R.id.registro_tipo)
        recyclerView = findViewById(R.id.registro_recyclerView_estudiante)
        fechaText = findViewById(R.id.registro_fec_seleccionada)

        //Declaro rutas de mensajes
        mensaje_apellido = findViewById(R.id.registro_mensaje_apellido)
        mensaje_fecNac = findViewById(R.id.registro_mensaje_fec_nac)
        mensaje_contrasena = findViewById(R.id.registro_mensaje_contrasena)
        mensaje_documento = findViewById(R.id.registro_mensaje_documento)
        mensaje_departamento = findViewById(R.id.registro_mensaje_departamento)
        mensaje_contrasenaConfirmar = findViewById(R.id.registro_mensaje_confirmar_contrasena)
        mensaje_emailInstitucional = findViewById(R.id.registro_mensaje_email_institucional)
        mensaje_emailPersonal = findViewById(R.id.registro_mensaje_email_personal)
        mensaje_emailPersonalConfirmar =
            findViewById(R.id.registro_mensaje_confirmar_email_personal)
        mensaje_genero = findViewById(R.id.registro_mensaje_genero)
        mensaje_itr = findViewById(R.id.registro_mensaje_itr)
        mensaje_localidad = findViewById(R.id.registro_mensaje_localidad)
        mensaje_nombre = findViewById(R.id.registro_mensaje_nombre)
        mensaje_nombreUsuario = findViewById(R.id.registro_mensaje_nombre_usuario)
        mensaje_telefono = findViewById(R.id.registro_mensaje_telefono)
        mensaje_tipoUsuario = findViewById(R.id.registro_mensaje_tipo)
        registro_mensaje = findViewById(R.id.registro_Mensaje)

        /*val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)*/
        val apiService = ApiClient.getApiService(this)
        //-------------Spinner ITR---------------------------------------------------------


        val callItr = apiService.obtenerITR()


        callItr.enqueue(object : Callback<List<Itr>> {
            override fun onResponse(call: Call<List<Itr>>, response: Response<List<Itr>>) {
                if (response.isSuccessful) {
                    val itrs = response.body() ?: emptyList()
                    Log.d("AgregarReclamoActivity", "API call successful. Itrs: $itrs")

                    // Configurar el ArrayAdapter
                    val itrAdapter = ItrAdapter(
                        this@RegistroActivity,
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
                            ) {
                                // Verificar que la posición seleccionada esté dentro de los límites
                                if (position >= 0 && position < itrs.size) {
                                    // Obtiene el itr seleccionado
                                    itrSeleccionado = itrs[position]

                                } else {
                                    // Puedes manejar esta situación según tus necesidades
                                    Log.e(
                                        "RegistroActivity",
                                        "Posición seleccionada fuera de los límites"
                                    )
                                }
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>) {
                                // Manejar caso cuando no hay nada seleccionado (si es necesario)
                            }

                        }
                } else {
                    Log.e("RegistroActivity", "API call failed with code ${response.code()}")
                    // Resto del código para manejar errores...
                }
            }

            override fun onFailure(call: Call<List<Itr>>, t: Throwable) {
                Log.e("ItrActivity", "API call failed", t)
                // Resto del código para manejar errores...
            }
        })


        //-------------Tipos Tutor-------------------------------------------------------

        // Listas de datos
        val tiposTutorList: MutableList<TipoTutorDTO> = mutableListOf()
        val tiposAreaList: MutableList<TipoAreaDTO> = mutableListOf()
        var tipoTutorSeleccionado = null

        // Función para obtener tipos de tutor con callback
        fun obtenerTiposTutor(callback: () -> Unit) {
            val callTiposTutor = apiService.obtenerTiposTutor()
            callTiposTutor.enqueue(object : Callback<List<TipoTutorDTO>> {
                override fun onResponse(
                    call: Call<List<TipoTutorDTO>>,
                    response: Response<List<TipoTutorDTO>>
                ) {
                    if (response.isSuccessful) {
                        tiposTutorList.clear()
                        tiposTutorList.addAll(response.body() ?: emptyList())
                        Log.d(
                            "RegistroActivity",
                            "API call successful. Tipos de Tutor: $tiposTutorList"
                        )
                        callback() // Llamar al callback cuando los datos estén listos
                    } else {
                        Log.e(
                            "RegistroActivity",
                            "API call failed for tipos de tutor with code ${response.code()}"
                        )
                        // Manejar error en la obtención de tipos de tutor
                    }
                }

                override fun onFailure(call: Call<List<TipoTutorDTO>>, t: Throwable) {
                    Log.e("RegistroActivity", "Failed to fetch tipos de tutor", t)
                    // Manejar fallo en la llamada para obtener tipos de tutor
                }
            })
        }

        // Función para obtener áreas con callback
        fun obtenerAreas(callback: () -> Unit) {
            val callAreas = apiService.obtenerAreas()
            callAreas.enqueue(object : Callback<List<TipoAreaDTO>> {
                override fun onResponse(
                    call: Call<List<TipoAreaDTO>>,
                    response: Response<List<TipoAreaDTO>>
                ) {
                    if (response.isSuccessful) {
                        tiposAreaList.clear()
                        tiposAreaList.addAll(response.body() ?: emptyList())
                        Log.d(
                            "RegistroActivity",
                            "API call successful. Tipos de Tutor: $tiposAreaList"
                        )
                        callback() // Llamar al callback cuando los datos estén listos
                    } else {
                        Log.e(
                            "RegistroActivity",
                            "API call failed for tipos de tutor with code ${response.code()}"
                        )
                        // Manejar error en la obtención de tipos de tutor
                    }
                }

                override fun onFailure(call: Call<List<TipoAreaDTO>>, t: Throwable) {
                    Log.e("RegistroActivity", "Failed to fetch area", t)
                    // Manejar fallo en la llamada para obtener tipos de tutor
                }
            })
        }

        // Llamada a las funciones y configuración del RecyclerView
        fun cargarDatosTutor() {
            obtenerTiposTutor {
                obtenerAreas {
                    // Aquí dentro, ambos llamados a la API han completado y las listas están llenas
                    Log.d("RegistroActivity", "Lista tipos: $tiposTutorList")
                    Log.d("RegistroActivity", "Lista areas: $tiposAreaList")

                    recyclerView.layoutManager = LinearLayoutManager(this@RegistroActivity)
                    val adapter = RegistroAdapter_tutor(tiposTutorList, tiposAreaList)
                    recyclerView.adapter = adapter
                }
            }
        }
        //-------------Spinner Roles------------------------------------------------------

        val callRol = apiService.obtenerRoles()


        callRol.enqueue(object : Callback<List<Rol>> {
            override fun onResponse(call: Call<List<Rol>>, response: Response<List<Rol>>) {
                if (response.isSuccessful) {
                    val roles = response.body() ?: emptyList()
                    Log.d("AgregarReclamoActivity", "API call successful. Rol: $roles")

                    // Configurar el ArrayAdapter
                    val rolAdapter = RolAdapter(
                        this@RegistroActivity,
                        roles
                    )

                    // Asignar el adapter al ListView
                    tipoUsuario.adapter = rolAdapter

                    tipoUsuario.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parentView: AdapterView<*>,
                                selectedItemView: View?,
                                position: Int,
                                id: Long
                            ) {
                                // Verificar que la posición seleccionada esté dentro de los límites
                                if (position >= 0 && position < roles.size) {
                                    // Obtiene el evento seleccionado
                                    rolSeleccionado = roles[position]

                                    when (rolSeleccionado!!.nombre) {
                                        "ESTUDIANTE" -> {
                                            recyclerView.layoutManager =
                                                LinearLayoutManager(this@RegistroActivity)
                                            val adapter = RegistroAdapter_estudiante()
                                            recyclerView.adapter = adapter

                                        }

                                        "TUTOR" -> {
                                            cargarDatosTutor()
                                        }

                                        "ANALISTA" -> {
                                            recyclerView.layoutManager =
                                                LinearLayoutManager(this@RegistroActivity)
                                            val adapter = RegistroAdapter_analista()
                                            recyclerView.adapter = adapter
                                        }
                                    }

                                } else {
                                    // Puedes manejar esta situación según tus necesidades
                                    Log.e(
                                        "RegistroActivity",
                                        "Posición seleccionada fuera de los límites"
                                    )
                                }
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>) {
                                // Manejar caso cuando no hay nada seleccionado (si es necesario)
                            }

                        }
                } else {
                    Log.e("RegistroActivity", "API call failed with code ${response.code()}")
                    // Resto del código para manejar errores...
                }
            }

            override fun onFailure(call: Call<List<Rol>>, t: Throwable) {
                Log.e("ItrActivity", "API call failed", t)
                // Resto del código para manejar errores...
            }
        })

        //-------------Spinner Departamentos-----------------------------------------------
        val listaDepartamentos = ArrayList<String>()
        var departamentoSeleccionado = ""

        listaDepartamentos.add("Artigas")
        listaDepartamentos.add("Canelones")
        listaDepartamentos.add("Cerro Largo")
        listaDepartamentos.add("Colonia")
        listaDepartamentos.add("Durazno")
        listaDepartamentos.add("Flores")
        listaDepartamentos.add("Florida")
        listaDepartamentos.add("Lavalleja")
        listaDepartamentos.add("Maldonado")
        listaDepartamentos.add("Montevideo")
        listaDepartamentos.add("Paysandú")
        listaDepartamentos.add("Río Negro")
        listaDepartamentos.add("Rivera")
        listaDepartamentos.add("Rocha")
        listaDepartamentos.add("Salto")
        listaDepartamentos.add("San José")
        listaDepartamentos.add("Soriano")
        listaDepartamentos.add("Tacuarembó")
        listaDepartamentos.add("Treinta y Tres")

        val departamentosAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDepartamentos)
        departamento.adapter = departamentosAdapter

        departamento.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    // Verificar que la posición seleccionada esté dentro de los límites
                    if (position >= 0 && position < listaDepartamentos.size) {
                        // Obtiene el departamento seleccionado
                        departamentoSeleccionado = listaDepartamentos[position]

                    } else {
                        // Puedes manejar esta situación según tus necesidades
                        Log.e(
                            "AgregarReclamoActivity",
                            "Posición seleccionada fuera de los límites"
                        )
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    // Manejar caso cuando no hay nada seleccionado (si es necesario)
                }

            }


        //-------------Spinner Genero-----------------------------------------------
        val listaGeneros = ArrayList<String>()
        var generoSeleccionado = ""

        listaGeneros.add("Maculino")
        listaGeneros.add("Femenino")


        val generosAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaGeneros)
        genero.adapter = generosAdapter

        genero.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    // Verificar que la posición seleccionada esté dentro de los límites
                    if (position >= 0 && position < listaGeneros.size) {
                        // Obtiene el departamento seleccionado
                        generoSeleccionado = listaGeneros[position]

                    } else {
                        Log.e(
                            "AgregarReclamoActivity",
                            "Posición seleccionada fuera de los límites"
                        )
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                }

            }


        //---------------------------------------------------------------------------------


        btnVolver.setOnClickListener {
            val loginActivity = Intent(this, LoginActivity::class.java)
            startActivity(loginActivity)
        }

        btnConfirmar.setOnClickListener {


            val camposVacios = mutableListOf<String>()



            if (documento.text.toString().isEmpty()) {
                camposVacios.add("Documento")
                mensaje_documento.text = "Ingresa un documento"
                mensaje_documento.alpha = 0.8f
                mensaje_documento.visibility = View.VISIBLE
            } else {
                mensaje_documento.visibility = View.INVISIBLE
            }

            if (nombre.text.toString().isEmpty()) {
                camposVacios.add("Nombre")
                mensaje_nombre.text = "Ingresa un nombre"
                mensaje_nombre.alpha = 0.8f
                mensaje_nombre.visibility = View.VISIBLE
            } else {
                mensaje_nombre.visibility = View.INVISIBLE
            }

            if (apellido.text.toString().isEmpty()) {
                camposVacios.add("Apellido")
                mensaje_apellido.text = "Ingresa un apellido"
                mensaje_apellido.alpha = 0.8f
                mensaje_apellido.visibility = View.VISIBLE // Mostrar el mensaje de error
            } else {
                mensaje_apellido.visibility =
                    View.INVISIBLE // Ocultar el mensaje de error si no está vacío
            }

            if (nombreUsuario.text.toString().isEmpty()) {
                camposVacios.add("Nombre usuario")
                mensaje_nombreUsuario.text = "Ingresa una contraseña"
                mensaje_nombreUsuario.alpha = 0.8f
                mensaje_nombreUsuario.visibility = View.VISIBLE
            } else {
                mensaje_nombreUsuario.visibility = View.INVISIBLE
            }

            if (contrasena.text.toString().isEmpty()) {
                camposVacios.add("Contraseña")
                mensaje_contrasena.text = "Ingresa una contraseña"
                mensaje_contrasena.alpha = 0.8f
                mensaje_contrasena.visibility = View.VISIBLE
            } else {
                mensaje_contrasena.visibility = View.INVISIBLE
            }

            if (contrasenaConfirmar.text.toString().isEmpty()) {
                camposVacios.add("Contraseña confirmar")
                mensaje_contrasenaConfirmar.text = "Confirma tu contraseña"
                mensaje_contrasenaConfirmar.alpha = 0.8f
                mensaje_contrasenaConfirmar.visibility = View.VISIBLE
            } else {
                mensaje_contrasenaConfirmar.visibility = View.INVISIBLE
            }

            if (emailInstitucional.text.toString().isEmpty()) {
                camposVacios.add("Email institucional")
                mensaje_emailInstitucional.text = "Ingresa un email institucional"
                mensaje_emailInstitucional.alpha = 0.8f
                mensaje_emailInstitucional.visibility = View.VISIBLE
            } else {
                mensaje_emailInstitucional.visibility = View.INVISIBLE
            }

            if (emailPersonal.text.toString().isEmpty()) {
                camposVacios.add("Email personal")
                mensaje_emailPersonal.text = "Ingresa un email personal"
                mensaje_emailPersonal.alpha = 0.8f
                mensaje_emailPersonal.visibility = View.VISIBLE
            } else {
                mensaje_emailPersonal.visibility = View.INVISIBLE
            }

            if (emailPersonalConfirmar.text.toString().isEmpty()) {
                camposVacios.add("Fecha de Nacimiento")
                mensaje_emailPersonalConfirmar.text = "Selecciona una fecha de nacimiento"
                mensaje_emailPersonalConfirmar.alpha = 0.8f
                mensaje_emailPersonalConfirmar.visibility = View.VISIBLE
            } else {
                mensaje_emailPersonalConfirmar.visibility = View.INVISIBLE
            }

            if (telefono.text.toString().isEmpty()) {
                camposVacios.add("Telefono")
                mensaje_telefono.text = "Ingresa un teléfono"
                mensaje_telefono.alpha = 0.8f
                mensaje_telefono.visibility = View.VISIBLE
            } else {
                mensaje_telefono.visibility = View.INVISIBLE
            }


            if (generoSeleccionado == "") {
                camposVacios.add("Genero")
                mensaje_genero.text = "Selecciona un género"
                mensaje_genero.alpha = 0.8f
                mensaje_genero.visibility = View.VISIBLE
            } else {
                mensaje_genero.visibility = View.INVISIBLE
            }

            if (fechaText.text.toString().isEmpty()) {
                camposVacios.add("Fec nac")
                mensaje_fecNac.text = "Selecciona una fecha de nacimiento"
                mensaje_fecNac.alpha = 0.8f
                mensaje_fecNac.visibility = View.VISIBLE
            } else {
                mensaje_fecNac.visibility = View.INVISIBLE
            }

            if (itrSeleccionado == null) {
                camposVacios.add("Itr")
                mensaje_itr.text = "Selecciona un itr"
                mensaje_itr.alpha = 0.8f
                mensaje_itr.visibility = View.VISIBLE
            } else {
                mensaje_itr.visibility = View.INVISIBLE
            }

            if (departamentoSeleccionado == "") {
                camposVacios.add("Departamento")
                mensaje_departamento.text = "Selecciona un departamento"
                mensaje_departamento.alpha = 0.8f
                mensaje_departamento.visibility = View.VISIBLE
            } else {
                mensaje_departamento.visibility = View.INVISIBLE
            }

            if (localidad.text.toString().isEmpty()) {
                camposVacios.add("Localidad")
                mensaje_localidad.text = "Ingresa una localidad"
                mensaje_localidad.alpha = 0.8f
                mensaje_localidad.visibility = View.VISIBLE
            } else {
                mensaje_localidad.visibility = View.INVISIBLE
            }

            if (camposVacios.isNotEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Todos los campos están completos
                val formatoFecha = SimpleDateFormat("dd/mm/yyyy")
                val nombre = nombre.text.toString()
                val apellido = apellido.text.toString()
                val contrasena = contrasena.text.toString()
                val documento = documento.text.toString().toInt()
                val nombreusuario = nombreUsuario.text.toString()
                val emailInstitucional = emailInstitucional.text.toString()
                val emailPersonal = emailPersonal.text.toString()
                val telefono = telefono.text.toString()
                val localidad = localidad.text.toString()
                var genero = ""
                if (generoSeleccionado == "Masculino") {
                    genero = "M"
                } else {
                    genero = "F"
                }
                //        val tipoUsuario=tipoUsuarioSeleccionado
                val fecNacString = fechaText.text.toString()
                val fecha = formatoFecha.parse(fecNacString)
                val fechaTimestamp = fecha.time

                Log.d("Registro Activity", "Datos: $genero $nombre $apellido")


                if (rolSeleccionado!!.nombre == "ESTUDIANTE") {

                    Log.d("Registro Activity", "Rol seleccionado: Estudiante")

                    val viewHolder =
                        recyclerView.findViewHolderForAdapterPosition(0) as RegistroAdapter_estudiante.ViewHolder

                    // Obtener los datos de "Año de Ingreso" y "Generación" a través del ViewHolder
                    val (generacion, anoDeIngreso) = RegistroAdapter_estudiante().obtenerDatosRegistro(
                        viewHolder
                    )


                    val usuarioNuevo = EstudianteDTO(
                        false,
                        apellido,
                        contrasena,
                        departamentoSeleccionado,
                        documento,
                        anoDeIngreso,
                        generacion,
                        genero,
                        null,
                        itrSeleccionado!!,
                        localidad,
                        emailInstitucional,
                        emailPersonal,
                        nombre,
                        rolSeleccionado!!,
                        telefono,
                        nombreusuario,
                        "ESTUDIANTE",
                        false
                    )

                    Log.d("Registro Activity", "Estudiante: $usuarioNuevo")

                    val callAgregarUsuarioEstudiante =
                        apiService.agregarUsuarioEstudiante(usuarioNuevo)

                    callAgregarUsuarioEstudiante.enqueue(object : Callback<EstudianteDTO> {
                        override fun onResponse(
                            call: Call<EstudianteDTO>,
                            response: Response<EstudianteDTO>
                        ) {
                            if (response.isSuccessful) {
                                val usuarioResp = response.body()
                                Log.d("Registro Activity", "ResponseBody: $usuarioResp")
                                Toast.makeText(
                                    this@RegistroActivity,
                                    "Usuario creado con éxito, pendiente de activación",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<EstudianteDTO>, t: Throwable) {
                            Log.d("Reclamo Activity", "error: ${t}")
                            registro_mensaje.text = "Ocurrió un error al crear el usuario"
                        }
                    })


                } else if (rolSeleccionado!!.nombre == "TUTOR") {
                    cargarDatosTutor()
                    Log.d("Registro Activity", "Rol seleccionado: Tutor")


                    // Obtener los datos de tutor del Viewholder
                    val tipo = RegistroAdapter_tutor(tiposTutorList,tiposAreaList).getSelectedTipoTutor(0)
                    val area = RegistroAdapter_tutor(tiposTutorList,tiposAreaList).getSelectedArea(0)



                    val usuarioNuevo = TutorDTO(
                        false,
                        apellido,
                        area,
                        contrasena,
                        departamentoSeleccionado,
                        documento,
                        fechaTimestamp,
                        genero,
                        null,
                        itrSeleccionado!!,
                        localidad,
                        emailInstitucional,
                        emailPersonal,
                        nombre,
                        rolSeleccionado!!,
                        telefono,
                        tipo,
                        nombreusuario,
                        "TUTOR",
                        false
                    )

                    Log.d("Registro Activity", "Tutor: $usuarioNuevo")

                    val callAgregarUsuarioTutor =
                        apiService.agregarUsuarioTutor(usuarioNuevo)

                    callAgregarUsuarioTutor.enqueue(object : Callback<TutorDTO> {
                        override fun onResponse(
                            call: Call<TutorDTO>,
                            response: Response<TutorDTO>
                        ) {
                            if (response.isSuccessful) {
                                val usuarioResp = response.body()
                                Log.d("Registro Activity", "ResponseBody: $usuarioResp")
                                Toast.makeText(
                                    this@RegistroActivity,
                                    "Usuario creado con éxito, pendiente de activación",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<TutorDTO>, t: Throwable) {
                            Log.d("Reclamo Activity", "error: ${t}")
                            registro_mensaje.text = "Ocurrió un error al crear el usuario"
                        }
                    })

                } else {
                    recyclerView.layoutManager = LinearLayoutManager(this@RegistroActivity)
                    val adapter = RegistroAdapter_analista()
                    recyclerView.adapter = adapter

                    Log.d("Registro Activity", "Rol seleccionado: Analista")

                    val usuarioNuevo = UsuarioDTO(
                        false,
                        apellido,
                        contrasena,
                        departamentoSeleccionado,
                        documento,
                        fechaTimestamp,
                        genero,
                        null,
                        itrSeleccionado!!,
                        localidad,
                        emailInstitucional,
                        emailPersonal,
                        nombre,
                        rolSeleccionado!!,
                        telefono,
                        nombreusuario,
                        "ANALISTA",
                        false
                    )
                    Log.d("Registro Activity", "Analista: $usuarioNuevo")

                    val callAgregarUsuarioAnalista =
                        apiService.agregarUsuario(usuarioNuevo)

                    callAgregarUsuarioAnalista.enqueue(object : Callback<UsuarioDTO> {
                        override fun onResponse(
                            call: Call<UsuarioDTO>,
                            response: Response<UsuarioDTO>
                        ) {
                            if (response.isSuccessful) {
                                val usuarioResp = response.body()
                                Log.d("Registro Activity", "ResponseBody: $usuarioResp")
                                Toast.makeText(
                                    this@RegistroActivity,
                                    "Usuario creado con éxito, pendiente de activación",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<UsuarioDTO>, t: Throwable) {
                            Log.d("Reclamo Activity", "error: ${t}")
                            registro_mensaje.text = "Ocurrió un error al crear el usuario"
                        }
                    })


                }
            }
      
        }

        fecNac.setOnClickListener(){
            mostrarCalendario()
        }
    }

    private fun mostrarCalendario() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->

                val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                fechaText.text = fechaSeleccionada
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}