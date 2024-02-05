package com.example.pft

data class Rol(
    val activo: Boolean,
    var descripcion: String?,
    val funcionalidades: List<Funcionalidade>?,
    var id: Int?,
    var nombre: String?
)