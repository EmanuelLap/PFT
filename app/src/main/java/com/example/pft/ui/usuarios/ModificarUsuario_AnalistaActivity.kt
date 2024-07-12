package com.example.pft.ui.usuarios

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.pft.ApiService
import com.example.pft.MainActivity
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.entidades.Evento
import com.example.pft.entidades.ReclamoDTOMobile
import com.example.pft.entidades.UsuarioDTO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ModificarUsuario_AnalistaActivity : AppCompatActivity() {

    private lateinit var nombre: TextView;
    private lateinit var apellido: TextView;
    private lateinit var itr: TextView;
    private lateinit var estado: TextView;
    private lateinit var btn_volver: FloatingActionButton
    private lateinit var btn_modificar: Button
    private lateinit var btn_baja: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_usuario_analista)

        // Recuperar la cadena JSON del Intent
        val usuarioJson = intent.getStringExtra("usuario")

        // Convertir la cadena JSON de vuelta a un objeto Evento (usando Gson)
        val usuarioSeleccionado = Gson().fromJson(usuarioJson, Usuario::class.java)

        nombre=findViewById(R.id.modificarUsuarioAnalista_nombre)
        apellido=findViewById(R.id.modificarUsuarioAnalista_apellido)
        itr=findViewById(R.id.modificarUsuarioAnalista_itr)
        estado=findViewById(R.id.modificarUsuarioAnalista_estado)
        btn_volver=findViewById(R.id.modificarUsuarioAnalista_btnVolver)
        btn_modificar=findViewById(R.id.modificarUsuarioAnalista_btnModificar)
        btn_baja=findViewById(R.id.modificarUsuarioAnalista_btnBaja)

        val usuarioUsuario=usuarioSeleccionado.usuario
        val usuarioActivo=usuarioSeleccionado.activo
        val usuarioId=usuarioSeleccionado.id
        val usuarioItr=usuarioSeleccionado.itr
        val usuarioApellidos=usuarioSeleccionado.apellidos
        val usuarioContrasenia=usuarioSeleccionado.contrasenia
        val usuariodepartamento=usuarioSeleccionado.departamento
        val usuariodocumento=usuarioSeleccionado.documento
        val usuariofecNac=usuarioSeleccionado.fechaNacimiento
        val usuariogenero=usuarioSeleccionado.genero
        val usuariolocalidad=usuarioSeleccionado.localidad
        val usuariomail=usuarioSeleccionado.mail
        val usuariomailPersonal=usuarioSeleccionado.mailPersonal
        val usuarionombres=usuarioSeleccionado.nombres
        val usuariorol=usuarioSeleccionado.rol
        val usuariotelefono=usuarioSeleccionado.telefono
        val usuarioTipo=usuarioSeleccionado.utipo
        val usuarioValidado=usuarioSeleccionado.validado
        val usuarioDTO= UsuarioDTO(usuarioActivo,usuarioApellidos,usuarioContrasenia,usuariodepartamento,usuariodocumento,usuariofecNac,usuariogenero,usuarioId,usuarioItr,"",usuariomail,"",usuarionombres,usuariorol,usuariotelefono,usuarioUsuario,"",usuarioValidado)

        nombre.text="Nombre: ${usuarioSeleccionado.nombres}"
        apellido.text="Apellido: ${usuarioSeleccionado.apellidos}"
        itr.text="Itr: ${usuarioSeleccionado.itr.nombre}"
        if(usuarioSeleccionado.activo==true) {
            estado.text = "Estado: Activo"
        } else{
            estado.text = "Estado: Inactivo"

        }

        btn_modificar.setOnClickListener{

        }

        btn_baja.setOnClickListener{
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")  // Reemplaza "tu_direccion_ip" con la dirección IP de tu máquina de desarrollo
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)
            val call = apiService.eliminarUsuario(
                usuarioDTO
            )
            Log.d("ModificarUsuarioActivity", "btnBaja")

            call.enqueue(object : Callback<UsuarioDTO> {
                override fun onResponse(call: Call<UsuarioDTO>, response: Response<UsuarioDTO>) {
                    if (response.isSuccessful) {
                        val reclamoResp = response.body()
                        val responseJson = Gson().toJson(reclamoResp)
                        Log.d("ReclamoActivity", "ResponseBody: $responseJson")
                        mostrarMensajeExito()
                    }
                }

                override fun onFailure(call: Call<UsuarioDTO>, t: Throwable) {
                    Log.d("ModificarUsuarioActivity", "error: ${t}")

                }
            })
        }

        btn_volver.setOnClickListener{
            finish()
        }
    }

    private fun mostrarMensajeExito() {
        runOnUiThread {
            val builder = AlertDialog.Builder(this@ModificarUsuario_AnalistaActivity)
            builder.setTitle("Éxito")
            builder.setMessage("Reclamo eliminado con éxito")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                val mainActivity = Intent(this@ModificarUsuario_AnalistaActivity, MainActivity::class.java)
                startActivity(mainActivity)
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}