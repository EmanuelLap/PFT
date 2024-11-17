package com.example.pft.ui.reclamos

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.pft.ApiClient
import com.example.pft.R
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.AccionReclamoDTO
import com.example.pft.entidades.AnalistaDTO
import com.example.pft.entidades.FuncionalidadDTO
import com.example.pft.entidades.Funcionalidades
import com.example.pft.entidades.ItrDTO
import com.example.pft.entidades.ReclamoDTO
import com.example.pft.entidades.ReclamoDTOMobile
import com.example.pft.entidades.RolDTO
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

private lateinit var respuesta: EditText
private lateinit var reclamo_mensaje: TextView
private lateinit var btn_responder: Button
private lateinit var btn_volver: Button



class ReclamoResponderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclamo_responder)

        respuesta=findViewById(R.id.reclamo_respuesta)
        btn_responder=findViewById(R.id.reclamo_btnResponder)
        btn_volver=findViewById(R.id.reclamo_btnVolver)
        reclamo_mensaje=findViewById(R.id.reclamoRespuesta_mensaje)

        // Recuperar el valor del "usuario"

        val usuario = UsuarioSingleton.usuario


        val reclamoJson = intent.getStringExtra("reclamo")

        // Convertir la cadena JSON de vuelta a un objeto Evento (usando Gson)
        val reclamoDTOSeleccionado = Gson().fromJson(reclamoJson, ReclamoDTO::class.java)


        btn_volver.setOnClickListener{
            val reclamoActivity = Intent(this@ReclamoResponderActivity, ReclamoActivity::class.java)
            reclamoActivity.putExtra("reclamo", reclamoJson)
            startActivity(reclamoActivity)
        }
        btn_responder.setOnClickListener {
            val camposVacios = mutableListOf<String>()

            if (respuesta.text.toString().isEmpty()) {
                camposVacios.add("respuesta")
                reclamo_mensaje.text = "La respuesta no puede estar vacía"
                reclamo_mensaje.alpha = 0.8f
                reclamo_mensaje.visibility = View.VISIBLE
            } else {
                reclamo_mensaje.visibility = View.INVISIBLE
            }

            if (camposVacios.isNotEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Todos los campos están completos

                val formatoFecha = SimpleDateFormat("dd/mm/yyyy")


                val contrasena=usuario?.contrasenia
                val apellidos=usuario?.apellidos
                val nombres=usuario?.nombres
                val fechaNacimiento=usuario?.fechaNacimiento
                val fechaNacimientoDate = Date(fechaNacimiento!!)
                val departamento=usuario?.departamento
                val genero=usuario?.genero
                val localidad=usuario?.localidad
                val mail=usuario?.mail
                val mailPersonal=usuario?.mailPersonal
                val telefono=usuario?.telefono
                val itr=usuario?.itr
                val itrDTO=ItrDTO(itr?.activo!!,itr?.departamento!!,itr?.id!!,itr?.nombre!!)
                val rol=usuario?.rol
                val funcionalidadDTOList: List<FuncionalidadDTO> = usuario.rol.funcionalidades.map { funcionalidad ->
                    // Convertimos cada Funcionalidades a FuncionalidadDTO
                    FuncionalidadDTO(
                        id = funcionalidad.id ?: 0, // Usamos 0 si id es null
                        nombre = funcionalidad.nombre ?: "", // Usamos una cadena vacía si nombre es null
                        descripcion = funcionalidad.descripcion ?: "", // Lo mismo para descripcion
                        activo = funcionalidad.activo ?: false // Usamos false si activo es null
                    )
                }
                val rolDTO= RolDTO(rol?.id!!,rol?.nombre!!,rol?.descripcion!!,rol?.activo!!,funcionalidadDTOList)
                val activo=usuario?.activo
                val validado=usuario?.validado
                val documento=usuario?.documento
                val usuario=usuario?.usuario

                val fechaActual = Date()

                val respuesta = respuesta.text.toString()
                val reclamoDTOSeleccionado = Gson().fromJson(reclamoJson, ReclamoDTO::class.java)



                val analista=AnalistaDTO(documento!!,usuario!!,contrasena!!,apellidos!!,nombres!!,fechaNacimientoDate!!,departamento!!,genero!!,localidad!!,mail!!,mailPersonal!!,telefono!!,itrDTO!!,rolDTO!!,activo!!,validado!!)

                val accion_reclamo = AccionReclamoDTO(null,respuesta,fechaActual,reclamoDTOSeleccionado,analista,reclamoDTOSeleccionado.activo)


                val apiService = ApiClient.getApiService(this)
                val call = apiService.agregarAccionReclamo(
                    accion_reclamo
                )

                call.enqueue(object : Callback<AccionReclamoDTO> {
                    override fun onResponse(
                        call: Call<AccionReclamoDTO>,
                        response: Response<AccionReclamoDTO>
                    ) {
                        if (response.isSuccessful) {
                            val reclamoResp = response.body()
                            Log.d("AgregarReclamoActivity", "ResponseBody: $reclamoResp")
                            mostrarDialogoRespuestaCreada()
                        }
                    }

                    override fun onFailure(call: Call<AccionReclamoDTO>, t: Throwable) {
                        Log.d("AgregarReclamoActivity", "error: ${t}")

                        reclamo_mensaje.text = "Ocurrió un error al crear el reclamo"
                        reclamo_mensaje.visibility = View.VISIBLE

                    }
                })

            }

        }




    }

    private fun mostrarDialogoRespuestaCreada() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Respuesta creado")
        builder.setMessage("La respuesta fue creada correctamente.")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            // Al hacer clic en Aceptar, cierra la Activity
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }
}