package com.example.pft.entidades

data class EventoId(
    val bajaLogica: Boolean,
    val fin: Long,
    val id: Int,
    val inicio: Long,
    val itrDTO: ItrDTO,
    val localizacion: String,
    val modalidadEvento: ModalidadEvento,
    val tipoEstadoEvento: TipoEstadoEvento,
    val tipoEvento: TipoEvento,
    val titulo: String,
    val tutorResponsableEventoDTOCollection: List<TutorResponsableEventoDTOCollection>



)
{
    constructor(evento: Evento) : this(
        id = evento.id,
        bajaLogica=evento.bajaLogica,
        fin=evento.fin,
        inicio=evento.inicio,
        itrDTO=evento.itrDTO,
        localizacion=evento.localizacion,
        modalidadEvento=evento.modalidadEvento,
        tipoEstadoEvento=evento.tipoEstadoEvento,
        tipoEvento=evento.tipoEvento,
        titulo=evento.titulo,
        tutorResponsableEventoDTOCollection=evento.tutorResponsableEventoDTOCollection,
    )
}

