package com.example.pft.entidades

import java.util.Date

data class RolDTO (
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val activo: Boolean,
    val funcionalidades: List<FuncionalidadDTO>,
)
