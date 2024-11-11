package com.example.pft.ui.eventos

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
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Spinner
import com.example.pft.ApiClient
import com.example.pft.ApiService
import com.example.pft.R
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.Evento
import com.example.pft.entidades.Itr
import com.example.pft.entidades.ModalidadEvento
import com.example.pft.entidades.TipoEvento
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
import java.util.Date
import java.util.concurrent.TimeUnit


class EventoFragment : Fragment() {

    private lateinit var fragmentContext: Context
    private var itrSeleccionado: Itr? = null
    private var tipoSeleccionado: TipoEvento? = null
    private var modalidadSeleccionada: ModalidadEvento? = null



    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_evento, container, false)
        val listaEventos = view.findViewById<ListView>(R.id.listaEventos)
        val layoutAnalista = view.findViewById<LinearLayout>(R.id.fragmentEvento_analista)
        val btnAgregar = view.findViewById<FloatingActionButton>(R.id.fragmentEvento_agregar)
        val itrSpinner = view.findViewById<Spinner>(R.id.Eventos_AnalistaActivity_itr)
        val tipoSpinner = view.findViewById<Spinner>(R.id.Eventos_AnalistaActivity_tipo)
        val modalidadSpinner = view.findViewById<Spinner>(R.id.Eventos_AnalistaActivity_modalidad)



        // Configurar la visibilidad del layout basado en la condición del usuario
        if (UsuarioSingleton.usuario?.rol?.nombre == "ANALISTA") {
            layoutAnalista.visibility = View.VISIBLE
            btnAgregar.visibility = View.VISIBLE
        } else {
            layoutAnalista.visibility = View.GONE
            btnAgregar.visibility = View.GONE
        }

        Log.d("EventoFragment", "onCreateView")

        /*val retrofit = Retrofit.Builder()
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
        val apiService = ApiClient.getApiService(requireContext())
        val call = apiService.obtenerEventos()



        //-------------Spinner ITR---------------------------------------------------------

        val callItr = apiService.obtenerITR()


        callItr.enqueue(object : Callback<List<Itr>> {
            override fun onResponse(call: Call<List<Itr>>, response: Response<List<Itr>>) {
                if (response.isSuccessful) {
                    val itrs = response.body() ?: emptyList()
                    Log.d("AgregarReclamoActivity", "API call successful. Itrs: $itrs")

                    // Configurar el ArrayAdapter
                    val itrAdapter = ItrAdapter(
                        requireContext(),
                        itrs
                    )

                    // Asignar el adapter al ListView
                    itrSpinner.adapter = itrAdapter

                    itrSpinner.onItemSelectedListener =
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


        //-------------Spinner Tipo---------------------------------------------------------

        val callTipo = apiService.obtenerTipos()


        callTipo.enqueue(object : Callback<List<TipoEvento>> {
            override fun onResponse(call: Call<List<TipoEvento>>, response: Response<List<TipoEvento>>) {
                if (response.isSuccessful) {
                    val tipos = response.body() ?: emptyList()
                    Log.d("AgregarReclamoActivity", "API call successful. Tipos: $tipos")

                    // Configurar el ArrayAdapter
                    val tipoAdapter = TipoEventoAdapter(
                        requireContext(),
                        tipos
                    )

                    // Asignar el adapter al ListView
                    tipoSpinner.adapter = tipoAdapter

                    tipoSpinner.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parentView: AdapterView<*>,
                                selectedItemView: View?,
                                position: Int,
                                id: Long
                            )  {
                                // Verificar que la posición seleccionada esté dentro de los límites
                                if (position >= 0 && position < tipos.size) {
                                    // Obtiene el itr seleccionado
                                    tipoSeleccionado = tipos[position]

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
                    Log.e("EventoFragment", "API call failed with code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<TipoEvento>>, t: Throwable) {
                Log.e("EventoFragment", "API call failed", t)
            }
        })


        //-------------Spinner Modalidad---------------------------------------------------------

        val callModalidad = apiService.obtenerModalidades()


        callModalidad.enqueue(object : Callback<List<ModalidadEvento>> {
            override fun onResponse(call: Call<List<ModalidadEvento>>, response: Response<List<ModalidadEvento>>) {
                if (response.isSuccessful) {
                    val modalidades = response.body() ?: emptyList()
                    Log.d("EventoFragment", "API call successful. Tipos: $modalidades")

                    // Configurar el ArrayAdapter
                    val modalidadEventoAdapter = ModalidadEventoAdapter(
                        requireContext(),
                        modalidades
                    )

                    // Asignar el adapter al ListView
                    modalidadSpinner.adapter = modalidadEventoAdapter

                    tipoSpinner.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parentView: AdapterView<*>,
                                selectedItemView: View?,
                                position: Int,
                                id: Long
                            )  {
                                // Verificar que la posición seleccionada esté dentro de los límites
                                if (position >= 0 && position < modalidades.size) {
                                    // Obtiene el itr seleccionado
                                    modalidadSeleccionada = modalidades[position]

                                } else {
                                    Log.e("EventoFragment", "Posición seleccionada fuera de los límites")
                                }
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>) {
                            }

                        }
                }else {
                    Log.e("EventoFragment", "API call failed with code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ModalidadEvento>>, t: Throwable) {
                Log.e("EventoFragment", "API call failed", t)
            }
        })

        //-------------------------------------------------------------------------------------



        val evento = Intent(fragmentContext, EventoActivity::class.java)

        Log.d("EventoFragment", "Before API call")

        call.enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if (isAdded && response.isSuccessful) {  // Verifica si el fragmento está añadido antes de acceder al contexto
                    val eventos = response.body()!!
                    Log.d("EventoFragment", "API call successful. Eventos: $eventos")

                    val adapter = ArrayAdapter(
                        fragmentContext,
                        android.R.layout.simple_list_item_1,
                        eventos.map { evento ->
                            val timestampInicio = evento.inicio
                            val timestampFin = evento.fin

                            // Convertir timestamps a fechas
                            val fechaInicio = Date(timestampInicio)
                            val fechaFin = Date(timestampFin)

                            // Define el formato que deseas para la fecha
                            val formato = SimpleDateFormat("dd/MM/yyyy")

                            // Formatear las fechas a String legible
                            val fechaInicioFormateada = formato.format(fechaInicio)
                            val fechaFinFormateada = formato.format(fechaFin)

                            // Construir el texto para cada evento con la fecha formateada
                            "${evento.titulo}\n${evento.modalidadEvento.nombre}\nInicio: $fechaInicioFormateada\nFin: $fechaFinFormateada"
                        }
                    )

                    listaEventos.adapter = adapter

                    listaEventos.setOnItemClickListener { _, _, i, _ ->
                        val eventoSeleccionado = eventos[i]
                        val eventoJson = Gson().toJson(eventoSeleccionado)
                        evento.putExtra("evento", eventoJson)
                        startActivity(evento)
                    }
                } else {
                    Log.e("EventoFragment", "API call failed with code ${response.code()}")
                    // Resto del código para manejar errores...
                }
            }

            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                if (isAdded) {  // Verifica si el fragmento está añadido antes de acceder al contexto
                    Log.e("EventoFragment", "API call failed", t)
                    // Resto del código para manejar errores...
                }
            }
        })

        btnAgregar.setOnClickListener {
            val agregarEventoActivity = Intent(fragmentContext, AgregarEventoActivity::class.java)
            startActivity(agregarEventoActivity)
        }

        return view
    }
}
