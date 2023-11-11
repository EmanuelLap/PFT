package com.example.pft.entidades

import com.google.gson.annotations.SerializedName


data class ModalidadEventoDTO (

  @SerializedName("id"     ) var id     : Int?     = null,
  @SerializedName("nombre" ) var nombre : String?  = null,
  @SerializedName("activo" ) var activo : Boolean? = null

)