package com.example.pft.entidades

import java.util.Date

data class AccionReclamoDTO (
    val detalle: String,
    val fecha: Date,
    val reclamoID: ReclamoDTO,
    val analistaId: AnalistaDTO
)
