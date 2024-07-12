package com.example.pft.entidades

data class Reclamo(
    val activo: Boolean,
    val creditos: Int,
    val detalle: String,
    val estudianteId: EstudianteId,
    val eventoId: EventoId,
    val fecha: Long,
    val id: Int,
    val semestre: Int,
    val titulo: String
)