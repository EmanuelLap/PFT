package com.example.pft.entidades

data class EventoDTOMobile(
    val bajaLogica: Boolean,
    val fin: Long,
    val id: Int?,
    val inicio: Long,
    val itrDTO: Int,
    val localizacion: String,
    val modalidadEvento: Int,
    val tipoEstadoEventoDTO: Int,
    val tipoEvento: Int,
    val titulo: String,
    val tutorResponsableEventoDTOCollection: List<Int>?
)