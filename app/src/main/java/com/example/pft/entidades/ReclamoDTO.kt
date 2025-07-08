package com.example.pft.entidades

import java.util.Date

data class ReclamoDTO(
    val activo: Boolean,
    val creditos: Int,
    val detalle: String,
    val estudianteId: EstudianteDTO,
    val eventoId: Evento,
    val TipoEstadoReclamoDTO: estadoReclamoId,
    val fechaEstadoReclamo: Long,
    val fecha: Long,
    val id: Int,
    val semestre: Int,
    val titulo: String
)