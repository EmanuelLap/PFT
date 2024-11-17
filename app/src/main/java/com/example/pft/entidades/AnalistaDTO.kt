package com.example.pft.entidades

import java.util.Date

data class AnalistaDTO (
    val documento: Int,
    val usuario: String,
    val contrasenia: String,
    val apellidos: String,
    val nombre: String,
    val fechaNacimiento: Date,
    val departamento: String,
    val genero: String,
    val localidad: String?,
    val mail: String,
    val mailPersonal: String,
    val telefono: String,
    val itr: ItrDTO,
    val rol: RolDTO,
    val activo: Boolean,
    val validado: Boolean,
)
