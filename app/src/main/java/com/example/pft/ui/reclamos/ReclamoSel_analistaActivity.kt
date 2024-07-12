package com.example.pft.ui.reclamos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.pft.R
import com.example.pft.entidades.Reclamo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

private lateinit var titulo: TextView;
private lateinit var detalle: TextView;
private lateinit var estado: TextView;
private lateinit var estadoActualizar: Spinner;
private lateinit var respuesta: EditText
private lateinit var btn_actualizar: Button
private lateinit var btn_volver: FloatingActionButton
