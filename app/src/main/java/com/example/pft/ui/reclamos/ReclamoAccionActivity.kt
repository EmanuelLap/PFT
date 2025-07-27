package com.example.pft.ui.reclamos

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pft.ApiClient
import com.example.pft.R
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.AccionReclamoDTO
import com.example.pft.entidades.AnalistaDTO
import com.example.pft.entidades.FuncionalidadDTO
import com.example.pft.entidades.ItrDTO
import com.example.pft.entidades.ReclamoDTO
import com.example.pft.entidades.RolDTO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date
import java.util.Locale


class ReclamoAccionActivity : AppCompatActivity() {

    private lateinit var detalle: EditText;
    private lateinit var agregarAccion: FloatingActionButton;
    private lateinit var volver: FloatingActionButton

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_reclamo_accion)

            val locale = Locale("es", "ES")
            Locale.setDefault(locale)

            detalle=findViewById(R.id.reclamoAccion_detalle)
            agregarAccion=findViewById(R.id.reclamoAccion_btnagregar)
            volver=findViewById(R.id.reclamoAccion_btnVolver)



            val reclamoJson = intent.getStringExtra("reclamo")

            // Convertir la cadena JSON de vuelta a un objeto Evento (usando Gson)
            val reclamoDTOSeleccionado = Gson().fromJson(reclamoJson, ReclamoDTO::class.java)

            // Recuperar el valor del "usuario"

            val usuario = UsuarioSingleton.usuario

            agregarAccion.setOnClickListener {

                if (detalle.text.toString().isEmpty()) {
                    Toast.makeText(this, "El detalle no puede estar vacío", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    val usuarioITRDTO=ItrDTO(
                        activo = usuario?.itr?.activo!!,
                        departamento=usuario.itr.departamento,
                        id=usuario.itr.id!!,
                        nombre=usuario.itr.nombre!!
                    )

                    val funcionalidades = usuario.rol.funcionalidades.map {
                        FuncionalidadDTO(it.id, it.nombre, it.descripcion, it.activo)
                    }

                    val usuarioRolDTO=RolDTO(
                        id = usuario.rol.id,
                        nombre=usuario.rol.nombre,
                        descripcion = usuario.rol.descripcion,
                        activo= usuario.rol.activo,
                        funcionalidades
                    )

                    val analistaDTO=AnalistaDTO(
                        activo = usuario.activo,
                        apellidos=usuario.apellidos,
                        contrasenia = usuario.contrasenia,
                        departamento=usuario.departamento,
                        documento=usuario.documento,
                        fechaNacimiento = usuario.fechaNacimiento,
                        genero=usuario.genero,
                        id=usuario.id,
                        itr=usuarioITRDTO,
                        localidad=usuario.localidad!!,
                        mail=usuario.mail,
                        mailPersonal = usuario.mailPersonal!!,
                        nombres=usuario.nombres,
                        rol=usuarioRolDTO,
                        telefono = usuario.telefono,
                        usuario=usuario.usuario,
                        validado=usuario.validado
                    )
                    val detalleIngresado = detalle.text.toString()
                    val fechaActual = Date()

                    Log.d("AccionReclamoActivity", "Fecha: $fechaActual")

                    val accionReclamo= AccionReclamoDTO(detalleIngresado,fechaActual,reclamoDTOSeleccionado,analistaDTO)

                    val apiService = ApiClient.getApiService(this)
                    val call = apiService.agregarAccionReclamo(
                        accionReclamo
                    )

                    call.enqueue(object : Callback<AccionReclamoDTO> {
                        override fun onResponse(
                            call: Call<AccionReclamoDTO>,
                            response: Response<AccionReclamoDTO>
                        ) {
                            if (response.isSuccessful) {
                                val reclamoResp = response.body()
                                Log.d("AgregarReclamoActivity", "ResponseBody: $reclamoResp")
                                mostrarDialogoAccionReclamoCreado()
                            }
                        }

                        override fun onFailure(call: Call<AccionReclamoDTO>, t: Throwable) {
                            Log.d("AgregarReclamoActivity", "error: ${t}")

                            Toast.makeText(this@ReclamoAccionActivity, "Ocurrió un error al registrar la acción", Toast.LENGTH_SHORT)
                                .show()                        }
                    })

                }
            }
            volver.setOnClickListener{
                finish()
            }


        }

    private fun mostrarDialogoAccionReclamoCreado() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Acción registrada")
        builder.setMessage("Su acción ha sido registrada correctamente.")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            // Al hacer clic en Aceptar, cierra la Activity
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }
}
