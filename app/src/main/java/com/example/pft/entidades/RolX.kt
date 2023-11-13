package com.example.pft.entidades

data class RolX(
    val activo: Boolean,
    val descripcion: String,
    val funcionalidades: List<Funcionalidade>,
    val id: Int,
    val nombre: String
)