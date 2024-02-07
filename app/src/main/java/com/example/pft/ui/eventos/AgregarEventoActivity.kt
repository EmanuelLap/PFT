package com.example.pft.ui.eventos

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.pft.ApiService
import com.example.pft.MainActivity_analista
import com.example.pft.R
import com.example.pft.entidades.Evento
import com.example.pft.entidades.Itr
import com.example.pft.entidades.ModalidadEvento
import com.example.pft.entidades.TipoEvento
import com.example.pft.entidades.UsuarioDTO
import com.example.pft.ui.login.ItrAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar

class AgregarEventoActivity : AppCompatActivity() {

    private lateinit var titulo: EditText;
    private lateinit var tipo: Spinner;
    private lateinit var modalidad: Spinner;
    private lateinit var itr: Spinner;
    private lateinit var localizacion: EditText;
    private lateinit var inicio: Button;
    private lateinit var inicioSeleccion: TextView;
    private lateinit var fin: Button;
    private lateinit var finSeleccion: TextView;
    private lateinit var tutoresAgregados: ListView;
    private lateinit var btnAsignarTutores: Button;
    private lateinit var btnVolver: FloatingActionButton;
    private lateinit var btnConfirmar: FloatingActionButton;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_evento)

        titulo=findViewById(R.id.agregarEvento_titulo)
        tipo=findViewById(R.id.agregarEvento_tipo)
        modalidad=findViewById(R.id.agregarEvento_modalidad)
        itr=findViewById(R.id.agregarEvento_itr)
        localizacion=findViewById(R.id.agregarEvento_localizacion)
        inicio=findViewById(R.id.agregarEvento_inicio)
        inicioSeleccion=findViewById(R.id.agregarEvento_inicio_seleccion)
        fin=findViewById(R.id.agregarEvento_fin)
        finSeleccion=findViewById(R.id.agregarEvento_fin_seleccion)
        tutoresAgregados=findViewById(R.id.agregarEvento_listaTutores)
        btnAsignarTutores=findViewById(R.id.agregarEvento_btnAsignarTutores)
        btnVolver=findViewById(R.id.agregarEvento_volver)
        btnConfirmar=findViewById(R.id.agregarEvento_agregar)


//cargo datos a spinners------------------------------

//Spinner evento_tipo

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")  // Reemplaza "tu_direccion_ip" con la dirección IP de tu máquina de desarrollo
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val callTipos = apiService.obtenerTipos()


        callTipos.enqueue(object : Callback<List<TipoEvento>> {
            override fun onResponse(call: Call<List<TipoEvento>>, response: Response<List<TipoEvento>>) {
                if (response.isSuccessful) {
                    val tiposEvento = response.body() ?: emptyList()
                    Log.d("AgregarEventoActivity", "API call successful. Tipos: $tiposEvento")

                    // Configurar el ArrayAdapter
                    val tiposEventoAdapter = TipoEventoAdapter(this@AgregarEventoActivity, tiposEvento)

                    // Asignar el adapter al ListView
                    tipo.adapter = tiposEventoAdapter

                    tipo.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parentView: AdapterView<*>,
                                selectedItemView: View?,
                                position: Int,
                                id: Long
                            )  {
                                // Verificar que la posición seleccionada esté dentro de los límites
                                if (position >= 0 && position < tiposEvento.size) {
                                    // Obtiene el evento seleccionado
                                    val tipoSeleccionado = tiposEvento[position]
                                } else {
                                    // Puedes manejar esta situación según tus necesidades
                                    Log.e("AgregarEventoActivity", "Posición seleccionada fuera de los límites")
                                }
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>) {
                            }
                        }
                }else {
                    Log.e("AgregarEventoActivity", "API call failed with code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<TipoEvento>>, t: Throwable) {
                Log.e("TipoEventoActivity", "API call failed", t)
            }
        })

        //Spinner modalidad

        val callModalidades = apiService.obtenerModalidades()


        callModalidades.enqueue(object : Callback<List<ModalidadEvento>> {
            override fun onResponse(call: Call<List<ModalidadEvento>>, response: Response<List<ModalidadEvento>>) {
                if (response.isSuccessful) {
                    val modalidadesEvento = response.body() ?: emptyList()
                    Log.d("AgregarEventoActivity", "API call successful. Modalidades: $modalidadesEvento")

                    // Configurar el ArrayAdapter
                    val modalidadesEventoAdapter = ModalidadEventoAdapter(this@AgregarEventoActivity, modalidadesEvento)

                    // Asignar el adapter al ListView
                    tipo.adapter = modalidadesEventoAdapter

                    tipo.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parentView: AdapterView<*>,
                                selectedItemView: View?,
                                position: Int,
                                id: Long
                            )  {
                                // Verificar que la posición seleccionada esté dentro de los límites
                                if (position >= 0 && position < modalidadesEvento.size) {
                                    // Obtiene el evento seleccionado
                                    val modalidadSeleccionada = modalidadesEvento[position]
                                } else {
                                    // Puedes manejar esta situación según tus necesidades
                                    Log.e("AgregarEventoActivity", "Posición seleccionada fuera de los límites")
                                }
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>) {
                            }
                        }
                }else {
                    Log.e("AgregarEventoActivity", "API call failed with code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ModalidadEvento>>, t: Throwable) {
                Log.e("AgregarEventoActivity", "API call failed", t)
            }
        })

        //-------------Spinner ITR---------------------------------------------------------

        val callItr = apiService.obtenerITR()


        callItr.enqueue(object : Callback<List<Itr>> {
            override fun onResponse(call: Call<List<Itr>>, response: Response<List<Itr>>) {
                if (response.isSuccessful) {
                    val itrs = response.body() ?: emptyList()
                    Log.d("AgregarEventoActivity", "API call successful. Itrs: $itrs")

                    // Configurar el ArrayAdapter
                    val itrAdapter = ItrAdapter(
                        this@AgregarEventoActivity,
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
                                    var itrSeleccionado = itrs[position]
                                } else {
                                    // Puedes manejar esta situación según tus necesidades
                                    Log.e("AgregarEventoActivity", "Posición seleccionada fuera de los límites")
                                }
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>) {
                            }
                        }
                }else {
                    Log.e("AgregarEventoActivity", "fallo en la api, códifo: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Itr>>, t: Throwable) {
                Log.e("AgregarEventoActivity", "API call failed", t)
            }
        })


        inicio.setOnClickListener{
        mostrarCalendarioInicio()
    }

    fin.setOnClickListener{
        mostrarCalendarioFin()
    }

    btnVolver.setOnClickListener{
        val mainActivity = Intent(this, MainActivity_analista::class.java)
        startActivity(mainActivity)
    }

        btnAsignarTutores.setOnClickListener{
            val asignarTutorActivity = Intent(this, AsignarTutorActivity::class.java)
            startActivity(asignarTutorActivity)
        }

        btnConfirmar.setOnClickListener{




            val camposVacios = mutableListOf<String>()



            if (titulo.text.toString().isEmpty()) {
                camposVacios.add("titulo")
                mensaje_titulo.text = "Ingresa un título"
                mensaje_titulo.alpha = 0.8f
                mensaje_titulo.visibility = View.VISIBLE
            } else {
                mensaje_titulo.visibility = View.INVISIBLE
            }

            if (tipo.)) {
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
                val tipoUsuario=tipoUsuarioSeleccionado
                val fecNacString=fechaText.text.toString()
                val fecha=formatoFecha.parse(fecNacString)
                val fechaTimestamp=fecha.time

                val usuarioNuevo= UsuarioDTO(false,apellido,contrasena,departamentoSeleccionado, documento, fechaTimestamp,genero,null,itrSeleccionado!!,localidad,emailInstitucional,emailPersonal,nombre,rol,telefono,nombreusuario,tipoUsuario,false )
                val callAgregarUsuario = apiService.agregarUsuario(usuarioNuevo)

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
            }
        }

}



private fun mostrarCalendarioInicio() {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)


    val datePickerDialog = DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->

            val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
            inicioSeleccion.text = fechaSeleccionada
        },
        year, month, day
    )
    datePickerDialog.show()
}

private fun mostrarCalendarioFin() {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)


    val datePickerDialog = DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->

            val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
            finSeleccion.text = fechaSeleccionada
        },
        year, month, day
    )
    datePickerDialog.show()
}


}