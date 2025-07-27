package com.example.pft.entidades

data class AnalistaDTO (
    val activo: Boolean,
    val apellidos: String,
    val contrasenia: String,
    val departamento: String,
    val documento: Int,
    val fechaNacimiento: Long,
    val genero: String,
    val id: Int?,
    val itr: ItrDTO,
    val localidad: Any,
    val mail: String,
    val mailPersonal: Any,
    val nombres: String,
    val rol: RolDTO,
    val telefono: String,
    val usuario: String,
    val validado: Boolean
)

