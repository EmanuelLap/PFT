package com.example.pft.entidades

import java.util.Date

data class AccionReclamoDTO (
    val id: Int,
    val detalle: String,
    val fecha: Date,
    val reclamoId: ReclamoDTO,
    val analistaId: AnalistaDTO,
    val activo: Boolean,

    )