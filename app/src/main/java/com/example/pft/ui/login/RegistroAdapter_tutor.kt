package com.example.pft.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.pft.R
import com.example.pft.entidades.AreaDTO
import com.example.pft.entidades.TipoAreaDTO
import com.example.pft.entidades.TipoDTO
import com.example.pft.entidades.TipoTutorDTO

class RegistroAdapter_tutor(
    private val tiposTutor: List<TipoDTO>,
    private val areas: List<AreaDTO>
) : RecyclerView.Adapter<RegistroAdapter_tutor.ViewHolder>() {

    // Variables para guardar los objetos seleccionados (en lugar de solo los nombres)
    private val selectedTipoTutores = mutableListOf<TipoDTO?>()
    private val selectedAreas = mutableListOf<AreaDTO?>()

    init {
        // Inicializar las listas con valores nulos para cada item
        for (i in 0 until itemCount) {
            selectedTipoTutores.add(null)
            selectedAreas.add(null)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tipoTutorSpinner: Spinner = itemView.findViewById(R.id.seccion_registro_tutor_rol)
        val areaSpinner: Spinner = itemView.findViewById(R.id.seccion_registro_tutor_area)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_seccion_registro_tutor, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 1 // Si tienes más items, cambia esto a la longitud de la lista que estás usando
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context

        // Configurar el adaptador para el Spinner 'tipoTutorSpinner'
        val tipoTutorAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, tiposTutor.map { it.nombre })
        tipoTutorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.tipoTutorSpinner.adapter = tipoTutorAdapter

        // Si ya tenemos un objeto seleccionado, lo preseleccionamos
        val selectedTipoTutor = selectedTipoTutores.getOrNull(holder.adapterPosition)
        selectedTipoTutor?.let {
            val index = tiposTutor.indexOfFirst { it == selectedTipoTutor }
            if (index != -1) holder.tipoTutorSpinner.setSelection(index)
        }

        // Configurar el adaptador para el Spinner 'areaSpinner'
        val areaAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, areas.map { it.nombre })
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.areaSpinner.adapter = areaAdapter

        // Si ya tenemos un objeto seleccionado, lo preseleccionamos
        val selectedArea = selectedAreas.getOrNull(holder.adapterPosition)
        selectedArea?.let {
            val index = areas.indexOfFirst { it == selectedArea }
            if (index != -1) holder.areaSpinner.setSelection(index)
        }

        // Listener para 'tipoTutorSpinner'
        holder.tipoTutorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Guardar el objeto seleccionado en lugar del nombre
                val adapterPosition = holder.adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    selectedTipoTutores[adapterPosition] = tiposTutor[position]
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Opcionalmente manejar el caso cuando no se selecciona nada
                val adapterPosition = holder.adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    selectedTipoTutores[adapterPosition] = null
                }
            }
        }

        // Listener para 'areaSpinner'
        holder.areaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Guardar el objeto seleccionado en lugar del nombre
                val adapterPosition = holder.adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    selectedAreas[adapterPosition] = areas[position]
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Opcionalmente manejar el caso cuando no se selecciona nada
                val adapterPosition = holder.adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    selectedAreas[adapterPosition] = null
                }
            }
        }
    }

    // Funciones para obtener los objetos seleccionados
    fun getSelectedTipoTutor(position: Int): TipoDTO? {
        return selectedTipoTutores.getOrNull(position)
    }

    fun getSelectedArea(position: Int): AreaDTO? {
        return selectedAreas.getOrNull(position)
    }
}
