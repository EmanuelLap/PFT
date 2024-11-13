package com.example.pft.entidades

data class TutorDTO(
    val activo: Boolean,
    val apellidos: String,
    val areaDTO: AreaDTO,
    val contrasenia: String,
    val departamento: String,
    val documento: Int,
    val fechaNacimiento: Long,
    val genero: String,
    val id: Int,
    val itr: Itr,
    val localidad: Any,
    val mail: String,
    val mailPersonal: Any,
    val nombres: String,
    val rol: Rol,
    val telefono: String,
    val tipoDTO: TipoDTO,
    val usuario: String,
    val utipo: String,
    val validado: Boolean
)