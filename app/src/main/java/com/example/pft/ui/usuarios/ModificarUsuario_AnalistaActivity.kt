package com.example.pft.ui.usuarios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.pft.MainActivity
import com.example.pft.R
import com.example.pft.Usuario
import com.example.pft.entidades.Evento
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class ModificarUsuario_AnalistaActivity : AppCompatActivity() {

    private lateinit var nombre: TextView;
    private lateinit var apellido: TextView;
    private lateinit var itr: TextView;
    private lateinit var estado: TextView;
    private lateinit var btn_volver: FloatingActionButton
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

        nombre.text="Nombre: ${usuarioSeleccionado.nombres}"
        apellido.text="Apellido: ${usuarioSeleccionado.apellidos}"
        itr.text="Itr: ${usuarioSeleccionado.itr.nombre}"
        if(usuarioSeleccionado.activo==true) {
            estado.text = "Estado: Activo"
        } else{
            estado.text = "Estado: Inactivo"

        }


        btn_volver.setOnClickListener{
            val mainActivity = Intent(this@ModificarUsuario_AnalistaActivity, Usuarios_AnalistaActivity::class.java)
            startActivity(mainActivity)
        }
    }
}