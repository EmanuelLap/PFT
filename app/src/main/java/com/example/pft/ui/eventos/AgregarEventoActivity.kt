package com.example.pft.ui.eventos

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
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.pft.ApiService
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.entidades.EventoDTOMobile
import com.example.pft.entidades.Itr
import com.example.pft.entidades.ItrDTO
import com.example.pft.entidades.ModalidadEvento
import com.example.pft.entidades.TipoEstadoEvento
import com.example.pft.entidades.TipoEvento
import com.example.pft.entidades.UsuarioDTO
import com.example.pft.ui.login.ItrAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit

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
    private lateinit var tutoresLista: ListView;
    private lateinit var btnAsignarTutores: Button;
    private lateinit var btnVolver: FloatingActionButton;
    private lateinit var btnConfirmar: FloatingActionButton;
    private lateinit var usuarios: List<Usuario>

    // Variable para almacenar tutores seleccionados
    private lateinit var tutoresSeleccionados: MutableList<Usuario>


    private var tipoSeleccionado: TipoEvento? = null
    private var modalidadSeleccionada: ModalidadEvento? = null
    private var itrDTOSeleccionado: ItrDTO? = null
    private var tipoEstado: TipoEstadoEvento? = null




    //mensajes

    private lateinit var mensaje_titulo:TextView
    private lateinit var mensaje_tipo:TextView
    private lateinit var mensaje_modalidad:TextView
    private lateinit var mensaje_itr:TextView
    private lateinit var mensaje_localizacion:TextView




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
        tutoresLista=findViewById(R.id.agregarEvento_listaTutores)

        tutoresLista.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        // Recibir la lista de tutores seleccionados del intent
        tutoresSeleccionados = intent.getParcelableArrayListExtra("tutoresSeleccionados") ?: mutableListOf()

        btnAsignarTutores=findViewById(R.id.agregarEvento_btnAsignarTutores)
        btnVolver=findViewById(R.id.agregarEvento_volver)
        btnConfirmar=findViewById(R.id.agregarEvento_agregar)

        //mensajes

        mensaje_titulo=findViewById(R.id.agregarEvento_mensaje_titulo)
        mensaje_localizacion=findViewById(R.id.agregarEvento_mensaje_localizacion)

        val tutoresAgregados = mutableListOf<UsuarioDTO>()

        usuarios = ArrayList()





//cargo datos a spinners------------------------------

//Spinner evento_tipo

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")  // Reemplaza "tu_direccion_ip" con la dirección IP de tu máquina de desarrollo
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

//---------------Tipo Estado-----------------------------------

        val callTipoEstados= apiService.obtenerTipoEstados()
        callTipoEstados.enqueue(object : Callback<List<TipoEstadoEvento>> {
            override fun onResponse(call: Call<List<TipoEstadoEvento>>, response: Response<List<TipoEstadoEvento>>) {
                if (response.isSuccessful) {
                    val tipoEstadosEvento = response.body() ?: emptyList()
                    Log.d("AgregarEventoActivity", "API call successful. Tipos: $tipoEstadosEvento")


                                    tipoEstado = tipoEstadosEvento[2]

                }else {
                    Log.e("AgregarEventoActivity", "API call failed with code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<TipoEstadoEvento>>, t: Throwable) {
                Log.e("TipoEventoActivity", "API call failed", t)
            }
        })

//------------------------Tipos------------------------------------

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
                                    tipoSeleccionado = tiposEvento[position]
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
                                    modalidadSeleccionada = modalidadesEvento[position]
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
                                    itrDTOSeleccionado=ItrDTO(true,itrSeleccionado.departamento,itrSeleccionado.id,itrSeleccionado.nombre)
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

        //----------Lista tutores------------------------------------
/*
        val callUsuarios = apiService.obtenerUsuarios()

        callUsuarios.enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                    if (response.isSuccessful) {
                        usuarios = response.body()!!
                        Log.d("AgregarEventoActivity", "API call successful. Usuarios: $usuarios")

                        val usuariosFiltrados = usuarios.filter { it.rol.nombre == "TUTOR" }


                        val adapter = ArrayAdapter(
                            this@AgregarEventoActivity,
                            android.R.layout.simple_list_item_1,
                            tutoresSeleccionados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}" }
                        )
                        tutoresLista.adapter = adapter
                    } else {
                        Log.e("API_CALL", "Error en la respuesta: ${response.code()}")
                    }
                }
            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {

                Log.e("UsuariosFragment", "API call failed", t)

            }
        })


 */

        val adapter = ArrayAdapter(
            this@AgregarEventoActivity,
            android.R.layout.simple_list_item_1,
            tutoresSeleccionados.map { "Nombre: ${it.nombres} ${it.apellidos}\nDocumento: ${it.documento}\nRol: ${it.rol.nombre}\nITR: ${it.itr.nombre}" }
        )
        tutoresLista.adapter = adapter

        inicio.setOnClickListener{
        mostrarCalendarioInicio()
    }

    fin.setOnClickListener{
        mostrarCalendarioFin()
    }

    btnVolver.setOnClickListener{
        finish()
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

            if (localizacion.text.toString().isEmpty()) {
                camposVacios.add("localizacion")
                mensaje_localizacion.text = "Ingresa una localización"
                mensaje_localizacion.alpha = 0.8f
                mensaje_localizacion.visibility = View.VISIBLE
            } else {
                mensaje_localizacion.visibility = View.INVISIBLE
            }

            if (camposVacios.isNotEmpty()) {
                Toast.makeText(this@AgregarEventoActivity, "Completa todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Todos los campos están completos
                val formatoFecha= SimpleDateFormat("dd/mm/yyyy")
                val titulo=titulo.text.toString()
                val localizacion=localizacion.text.toString()
                val fechaInicioString=inicioSeleccion.text.toString()
                val fechaInicio=formatoFecha.parse(fechaInicioString)
                val fechaInicioTimestamp=fechaInicio.time
                val fechaFinString=finSeleccion.text.toString()
                val fechaFin=formatoFecha.parse(fechaFinString)
                val fechaFinTimestamp=fechaFin.time





                val evento= EventoDTOMobile(false,fechaFinTimestamp,null,fechaInicioTimestamp,itrDTOSeleccionado!!,localizacion,modalidadSeleccionada!!,tipoEstado!!,tipoSeleccionado!!,titulo,null)
                val callAgregarEvento = apiService.agregarEvento(evento)

                callAgregarEvento.enqueue(object : Callback<EventoDTOMobile> {
                    override fun onResponse(call: Call<EventoDTOMobile>, response: Response<EventoDTOMobile>) {
                        if (response.isSuccessful) {
                            val usuarioResp = response.body()
                            val responseJson = Gson().toJson(usuarioResp)
                            Log.d("Registro Activity", "ResponseBody: $responseJson")
                            Toast.makeText(this@AgregarEventoActivity, "Evento creado con éxito, pendiente de activación", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<EventoDTOMobile>, t: Throwable) {
                        Log.d("AgregarEvento Activity", "error: ${t}")

                        Toast.makeText(this@AgregarEventoActivity, "Ocurrió un error al crear el evento", Toast.LENGTH_SHORT).show()
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