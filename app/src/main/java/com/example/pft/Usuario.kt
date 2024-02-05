package com.example.pft

import com.example.pft.entidades.Itr
import com.example.pft.entidades.Rol

data class Usuario(
    val activo: Boolean,
    val apellidos: String,
    val contrasenia: String,
    val departamento: String,
    val documento: Int,
    val fechaNacimiento: Long,
    val genero: String,
    val id: Int?,
    val itr: Itr,
    val localidad: String,
    val mail: String,
    val mailPersonal: String,
    val nombres: String,
    val rol: Rol,
    val telefono: String,
    val usuario: String,
    val utipo: String,
    val validado: Boolean
)