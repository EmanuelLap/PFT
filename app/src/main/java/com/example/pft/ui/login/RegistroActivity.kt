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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pft.ApiService
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.entidades.Funcionalidades
import com.example.pft.entidades.Itr
import com.example.pft.entidades.Rol
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
    private lateinit var btnConfirmar:FloatingActionButton

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
    private lateinit var genero: EditText
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
       btnVolver=findViewById(R.id.registro_btnVolver)
       btnConfirmar=findViewById(R.id.registro_btnConfirmar)
       documento=findViewById(R.id.registro_documento)
       nombre=findViewById(R.id.registro_nombre)
       apellido=findViewById(R.id.registro_apellido)
       nombreUsuario=findViewById(R.id.registro_nombre_usuario)
       contrasena=findViewById(R.id.registro_contrasena)
       contrasenaConfirmar=findViewById(R.id.registro_confirmar_contrasena)
       emailInstitucional=findViewById(R.id.registro_email_institucional)
       emailPersonal=findViewById(R.id.registro_email_personal)
       emailPersonalConfirmar=findViewById(R.id.registro_confirmar_email_personal)
       telefono=findViewById(R.id.registro_telefono)
       genero=findViewById(R.id.registro_genero)
       fecNac=findViewById(R.id.registro_fec_nac)
       itr=findViewById(R.id.registro_itr)
       departamento=findViewById(R.id.registro_departamento)
       localidad=findViewById(R.id.registro_localidad)
       tipoUsuario=findViewById(R.id.registro_tipo)
       recyclerView=findViewById(R.id.registro_recyclerView_estudiante)
       fechaText=findViewById(R.id.registro_fec_seleccionada)

        //Declaro rutas de mensajes
        mensaje_apellido=findViewById(R.id.registro_mensaje_apellido)
        mensaje_fecNac=findViewById(R.id.registro_mensaje_fec_nac)
        mensaje_contrasena=findViewById(R.id.registro_mensaje_contrasena)
        mensaje_documento=findViewById(R.id.registro_mensaje_documento)
        mensaje_departamento=findViewById(R.id.registro_mensaje_departamento)
        mensaje_contrasenaConfirmar=findViewById(R.id.registro_mensaje_confirmar_contrasena)
        mensaje_emailInstitucional=findViewById(R.id.registro_mensaje_email_institucional)
        mensaje_emailPersonal=findViewById(R.id.registro_mensaje_email_personal)
        mensaje_emailPersonalConfirmar=findViewById(R.id.registro_mensaje_confirmar_email_personal)
        mensaje_genero=findViewById(R.id.registro_mensaje_genero)
        mensaje_itr=findViewById(R.id.registro_mensaje_itr)
        mensaje_localidad=findViewById(R.id.registro_mensaje_localidad)
        mensaje_nombre=findViewById(R.id.registro_mensaje_nombre)
        mensaje_nombreUsuario=findViewById(R.id.registro_mensaje_nombre_usuario)
        mensaje_telefono=findViewById(R.id.registro_mensaje_telefono)
        mensaje_tipoUsuario=findViewById(R.id.registro_mensaje_tipo)
        registro_mensaje=findViewById(R.id.registro_Mensaje)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

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
                            )  {
                                // Verificar que la posición seleccionada esté dentro de los límites
                                if (position >= 0 && position < itrs.size) {
                                    // Obtiene el itr seleccionado
                                    itrSeleccionado = itrs[position]

                                } else {
                                    // Puedes manejar esta situación según tus necesidades
                                    Log.e("RegistroActivity", "Posición seleccionada fuera de los límites")
                                }
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>) {
                                // Manejar caso cuando no hay nada seleccionado (si es necesario)
                            }

                        }
                }else {
                    Log.e("RegistroActivity", "API call failed with code ${response.code()}")
                    // Resto del código para manejar errores...
                }
            }

            override fun onFailure(call: Call<List<Itr>>, t: Throwable) {
                Log.e("ItrActivity", "API call failed", t)
                // Resto del código para manejar errores...
            }
        })


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
                            )  {
                                // Verificar que la posición seleccionada esté dentro de los límites
                                if (position >= 0 && position < roles.size) {
                                    // Obtiene el evento seleccionado
                                    rolSeleccionado = roles[position]

                                    when (rolSeleccionado!!.nombre) {
                                       "ESTUDIANTE" -> {
                                            recyclerView.layoutManager = LinearLayoutManager(this@RegistroActivity)
                                            val adapter = RegistroAdapter_estudiante()
                                            recyclerView.adapter = adapter

                                        }

                                        "TUTOR" -> {
                                            recyclerView.layoutManager = LinearLayoutManager(this@RegistroActivity)
                                            val adapter = RegistroAdapter_tutor()
                                            recyclerView.adapter = adapter
                                        }

                                        "ANALISTA" -> {
                                            recyclerView.layoutManager = LinearLayoutManager(this@RegistroActivity)
                                            val adapter = RegistroAdapter_analista()
                                            recyclerView.adapter = adapter
                                        }
                                    }

                                } else {
                                    // Puedes manejar esta situación según tus necesidades
                                    Log.e("RegistroActivity", "Posición seleccionada fuera de los límites")
                                }
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>) {
                                // Manejar caso cuando no hay nada seleccionado (si es necesario)
                            }

                        }
                }else {
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
        val listaDepartamentos=ArrayList<String>()
        var departamentoSeleccionado=""

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

        val departamentosAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,listaDepartamentos)
        departamento.adapter=departamentosAdapter

        departamento.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                )  {
                    // Verificar que la posición seleccionada esté dentro de los límites
                    if (position >= 0 && position < listaDepartamentos.size) {
                        // Obtiene el departamento seleccionado
                         departamentoSeleccionado = listaDepartamentos[position]

                    } else {
                        // Puedes manejar esta situación según tus necesidades
                        Log.e("AgregarReclamoActivity", "Posición seleccionada fuera de los límites")
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    // Manejar caso cuando no hay nada seleccionado (si es necesario)
                }

            }


        //---------------------------------------------------------------------------------


        btnVolver.setOnClickListener {
            val loginActivity = Intent(this, LoginActivity::class.java)
            startActivity(loginActivity)
        }

        btnConfirmar.setOnClickListener{




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
                mensaje_apellido.visibility = View.INVISIBLE // Ocultar el mensaje de error si no está vacío
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

            if (genero.text.toString().isEmpty()) {
                camposVacios.add("Género")
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

            if (itrSeleccionado==null) {
                camposVacios.add("Itr")
                mensaje_itr.text = "Selecciona un itr"
                mensaje_itr.alpha = 0.8f
                mensaje_itr.visibility = View.VISIBLE
            } else {
                mensaje_itr.visibility = View.INVISIBLE
            }

            if (departamentoSeleccionado=="") {
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
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Todos los campos están completos
                val formatoFecha= SimpleDateFormat("dd/mm/yyyy")
                val nombre=nombre.text.toString()
                val apellido=apellido.text.toString()
                val contrasena=contrasena.text.toString()
                val documento=documento.text.toString().toInt()
                val nombreusuario=nombreUsuario.text.toString()
                val emailInstitucional=emailInstitucional.text.toString()
                val emailPersonal=emailPersonal.text.toString()
                val telefono=telefono.text.toString()
                val genero=genero.text.toString()
                val localidad=localidad.text.toString()
        //        val tipoUsuario=tipoUsuarioSeleccionado
                val fecNacString=fechaText.text.toString()
                val fecha=formatoFecha.parse(fecNacString)
                val fechaTimestamp=fecha.time

             //   val usuarioNuevo=UsuarioDTO(false,apellido,contrasena,departamentoSeleccionado, documento, fechaTimestamp,genero,null,itrSeleccionado!!,localidad,emailInstitucional,emailPersonal,nombre,rol,telefono,nombreusuario,tipoUsuario,false )
              //  val callAgregarUsuario = apiService.agregarUsuario(usuarioNuevo)
/*
                callAgregarUsuario.enqueue(object : Callback<UsuarioDTO> {
                    override fun onResponse(call: Call<UsuarioDTO>, response: Response<UsuarioDTO>) {
                        if (response.isSuccessful) {
                            val usuarioResp = response.body()
                            val responseJson = Gson().toJson(usuarioResp)
                            Log.d("Registro Activity", "ResponseBody: $responseJson")
                            Toast.makeText(this@RegistroActivity, "Usuario creado con éxito, pendiente de activación", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UsuarioDTO>, t: Throwable) {
                        Log.d("Reclamo Activity", "error: ${t}")

                        registro_mensaje.text="Ocurrió un error al crear el usuario"
                    }
                })

 */
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