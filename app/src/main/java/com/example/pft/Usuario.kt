package com.example.pft

data class Usuario(
    val activo: Boolean,
    val apellidos: String,
    val contrasenia: String,
    val departamento: String,
    val documento: Int,
    val fechaNacimiento: Long,
    val genero: String,
    val id: Int,
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