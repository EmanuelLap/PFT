package com.example.pft.ui.reclamos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import com.example.pft.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ReclamoFragment : Fragment() {

    final lateinit var fab : FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_reclamo, container, false)

        fab = view.findViewById(R.id.reclamos_agregar)

        fab.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), fab)
            popupMenu.menuInflater.inflate(R.menu.reclamo_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->

                val fragmentManager = requireActivity().supportFragmentManager

                when (item.itemId) {
                    R.id.opcion1 -> {
                        val fragmentOpcion1 = AgregarReclamoFragment()
                       replaceFragment(fragmentManager,
                           R.id.nav_host_fragment_content_main,fragmentOpcion1)
                        true
                    }
                    R.id.opcion2 -> {
                        val fragmentOpcion2 = AgregarReclamoVMEFragment()
                        replaceFragment(fragmentManager,R.id.nav_host_fragment_content_main, fragmentOpcion2)
                        true
                    }
                    R.id.opcion3 -> {
                        val fragmentOpcion3 = AgregarReclamoAPE_OPTFragment()
                        replaceFragment(fragmentManager,
                            R.id.nav_host_fragment_content_main, fragmentOpcion3)
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
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
