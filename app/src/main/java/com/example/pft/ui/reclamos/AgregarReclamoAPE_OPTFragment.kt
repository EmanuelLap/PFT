package com.example.pft.ui.reclamos

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.pft.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar
import java.util.Locale



class AgregarReclamoAPE_OPTFragment : Fragment() {


    private lateinit var titulo: EditText
    private lateinit var descripcion:EditText
    private lateinit var nombre_actividad:EditText
    private lateinit var creditos:EditText
    private lateinit var fechaButton: Button
    private lateinit var fechaText: TextView
    private lateinit var semestre: Spinner
    private lateinit var docente: Spinner
    private lateinit var agregarReclamo: FloatingActionButton
    private lateinit var volver: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_agregar_reclamo_a_p_e__o_p_t, container, false)

        //Declaro rutas a elementos
        titulo=view.findViewById(R.id.agregarReclamoAPE_OPT_Titulo)
        descripcion=view.findViewById(R.id.agregarReclamoAPE_OPT_Descripcion)
        nombre_actividad=view.findViewById(R.id.agregarReclamoAPE_OPT_actividad)
        creditos=view.findViewById(R.id.agregarReclamoAPE_OPT_creditos)
        fechaButton = view.findViewById(R.id.agregarReclamoAPE_OPT_fecha)
        fechaText=view.findViewById(R.id.agregarReclamoAPE_OPT_fecha_seleccionada)
        semestre=view.findViewById(R.id.agregarReclamoAPE_OPT_semestre)
        agregarReclamo=view.findViewById(R.id.agregarReclamoAPE_OPT_agregar)
        docente=view.findViewById(R.id.agregarReclamoAPE_OPT_docente)
        volver=view.findViewById(R.id.agregarReclamoAPE_OPT_volver)

        //spinner semestre
        // opciones (del 1 al 10)
        val semestreOpciones = ArrayList<String>()
        semestreOpciones.add("1")
        semestreOpciones.add("2")
        semestreOpciones.add("3")
        semestreOpciones.add("4")
        semestreOpciones.add("5")
        semestreOpciones.add("6")
        semestreOpciones.add("7")
        semestreOpciones.add("8")
        semestreOpciones.add("9")
        semestreOpciones.add("10")

        // Crear un ArrayAdapter y establecerlo en el Spinner
        val semestreAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, semestreOpciones)
        semestreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        semestre.adapter = semestreAdapter

        // Establecer el "hint" en el Spinner
        semestre.prompt = "Selecciona un semestre"

        //Spinner docente
        //opciones
        val docenteOpciones=ArrayList<String>()
        docenteOpciones.add("docente 1")
        docenteOpciones.add("docente 2")
        docenteOpciones.add("docente 3")
        docenteOpciones.add("docente 4")
        docenteOpciones.add("docente 5")

        val docenteAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, docenteOpciones)
        docenteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        docente.adapter = docenteAdapter

        // Establecer el "hint" en el Spinner
        semestre.prompt = "Selecciona un docente"


        fechaButton.setOnClickListener {
            mostrarCalendario()
        }

        volver.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            replaceFragment(fragmentManager,
                R.id.nav_host_fragment_content_main,ReclamoFragment())
        }


        return view
    }

    private fun mostrarCalendario() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->

                val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                fechaText.text = fechaSeleccionada
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun replaceFragment(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    }
