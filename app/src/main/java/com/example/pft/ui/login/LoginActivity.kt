package com.example.pft.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.pft.ApiService
import com.example.pft.MainActivity
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.UsuarioSingleton
import com.example.pft.entidades.LoginResponse
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private lateinit var btnRegistrar:TextView
    private lateinit var btnIngresar:Button
    private lateinit var inputUsuario:EditText
    private lateinit var inputContrasena:EditText
    private lateinit var mensaje:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)


        btnRegistrar = findViewById(R.id.login_registrar)
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

//                val call = apiService.autenticarUsuario(usuarioIngresado, contrasenaIngresada)
                val credenciales =Credenciales(usuarioIngresado, contrasenaIngresada) ;
//                val credenciales =Credenciales("admin", "1")
//                val call = apiService.autenticarUsuario(username = usuarioIngresado, password = contrasenaIngresada)
                val call = apiService.login(credenciales)
                call.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val logResp = response.body()

                            val responseJson= Gson().toJson(logResp)
                            Log.d("LoginActivity", "ResponseBody: $logResp")
                            if (logResp?.token != null && logResp.token!!.isNotEmpty()) {


                                // Guardamos  en SharedPreferences
                                val sharedPreferences = getSharedPreferences("PREFS_NAME", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("token", logResp.token)
                                editor.apply()



                    try { // Parsear el JSON
                        val jsonObject = JSONObject(responseJson)
                        val userJson = jsonObject.getJSONObject("user")

                        // Utilizar Gson para convertir el JSON a un objeto Usuario
                        val gson = Gson()
                        val usuario = gson.fromJson(userJson.toString(), Usuario::class.java)

                        // Asignar el objeto Usuario a UsuarioSingleton
                        UsuarioSingleton.usuario = usuario

                        // Crear un Intent para abrir MainActivity
                        val mainActivity = Intent(this@LoginActivity, MainActivity::class.java)

                        // Iniciar MainActivity
                        startActivity(mainActivity)

                        // Finalizar esta actividad si se desea
                        finish()
                    } catch (e: JSONException) {
                        // Manejar la excepción si ocurre un error al parsear el JSON
                        Log.e("Error", "Error al parsear JSON: ${e.message}")
                    }


                            } else {
                                // La respuesta fue exitosa, pero no contiene un token válido.
                                mensaje.alpha = 0.8f
                                mensaje.text = "No se recibió un token válido."
                            }
                        } else {
                            mensaje.alpha = 0.8f
                            mensaje.text = "Error: ${response.code()} - ${response.message()}"
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        mensaje.alpha = 0.8f
                        if(t is IOException) {
                            mensaje.text = "Error de conexión: ${t.message}"
                        } else {
                            mensaje.text = "Error de conversión: ${t.message}"
                        }
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
