package com.example.pft.ui.eventos

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import com.example.pft.MainActivity_analista
import com.example.pft.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
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