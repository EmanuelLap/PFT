package com.example.pft.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pft.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RegistroActivity : AppCompatActivity() {

    private lateinit var btnVolver: FloatingActionButton
    private lateinit var  btnConfirmar:FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

       btnVolver=findViewById(R.id.registro_btnVolver)
       btnConfirmar=findViewById(R.id.registro_btnConfirmar)

        btnVolver.setOnClickListener {
            val loginActivity = Intent(this, LoginActivity::class.java)
            startActivity(loginActivity)
        }
    }
}