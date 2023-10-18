package com.example.pft.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.pft.ApiService
import com.example.pft.MainActivity
import com.example.pft.R
import com.example.pft.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var btnRegistrar:TextView
    private lateinit var btnRecuperarContrasena:TextView
    private lateinit var btnIngresar:Button
    private lateinit var inputUsuario:EditText
    private lateinit var inputContrasena:EditText
    private lateinit var mensaje:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)


        btnRegistrar = findViewById(R.id.login_registrar)
        btnRecuperarContrasena = findViewById(R.id.login_recuperar_contrasena)
        btnIngresar = findViewById(R.id.login_ingresar)
        mensaje = findViewById(R.id.login_mensaje)
        inputUsuario=findViewById(R.id.login_usuario)
        inputContrasena=findViewById(R.id.login_contrasena)



        btnIngresar.setOnClickListener {
            var usuarioIngresado = inputUsuario.text.toString()
            var contrasenaIngresada = inputContrasena.text.toString()


            if (usuarioIngresado =="") {
                mensaje.alpha=0.8f
                mensaje.text = "Ingresa un nombre de usuario"
            } else if (contrasenaIngresada =="") {
                mensaje.alpha=0.8f
                mensaje.text = "Ingresa una contraseña"
            } else {
                val call = apiService.autenticarUsuario(usuarioIngresado, contrasenaIngresada)
                call.enqueue(object : Callback<Usuario> {
                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        if (response.isSuccessful) {
                            val mainActivity = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(mainActivity)
                        }
                    }

                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        mensaje.alpha = 0.8f
                        mensaje.text = "Usuario o contraseña incorrectos"
                        }
                })
            }

        }

        btnRegistrar.setOnClickListener {
            val registrarActivity = Intent(this, RegistroActivity::class.java)
            startActivity(registrarActivity)
        }


        }

    }
