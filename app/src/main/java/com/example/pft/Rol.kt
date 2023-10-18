package com.example.pft

data class Rol(
    val activo: Boolean,
    val descripcion: String,
    val funcionalidades: List<Funcionalidade>,
    val id: Int,
    val nombre: String
)