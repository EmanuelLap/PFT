package com.example.pft.entidades

data class Evento(
    val bajaLogica: Boolean,
    val fin: Long,
    val id: Int,
    val inicio: Long,
    val itr: Itr,
    val localizacion: String,
    val modalidadEvento: ModalidadEvento,
    val tipoEstadoEvento: TipoEstadoEvento,
    val tipoEvento: TipoEvento,
    val titulo: String,
    val tutorResponsableEventoDTOCollection: List<TutorResponsableEventoDTOCollection>
)