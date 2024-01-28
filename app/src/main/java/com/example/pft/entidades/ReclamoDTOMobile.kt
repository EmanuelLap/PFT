package com.example.pft.entidades

data class ReclamoDTOMobile(
    val activo: Boolean,
    val creditos: Int,
    val detalle: String,
    val estudianteId: Int,
    val eventoId: Int,
    val fecha: Long,
    val id: Int?,
    val semestre: Any,
    val titulo: String
)