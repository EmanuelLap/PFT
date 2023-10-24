package com.example.pft.ui.reclamos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.pft.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

private lateinit var titulo: EditText
private lateinit var descripcion: EditText
private lateinit var agregarReclamo: FloatingActionButton
private lateinit var volver: FloatingActionButton
class AgregarReclamoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_agregar_reclamo, container, false)

        titulo=view.findViewById(R.id.agregarReclamo_Titulo)
        descripcion=view.findViewById(R.id.agregarReclamo_Descripcion)
        agregarReclamo=view.findViewById(R.id.agregarReclamo_agregar)
        volver=view.findViewById(R.id.agregarReclamo_volver)

        volver.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            replaceFragment(fragmentManager,
                R.id.nav_host_fragment_content_main,ReclamoFragment())
        }

        return view
    }

    private fun replaceFragment(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    }
