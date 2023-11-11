package com.example.pft.entidades

import com.google.gson.annotations.SerializedName


data class ItrDTO (

  @SerializedName("id"           ) var id           : Int?     = null,
  @SerializedName("departamento" ) var departamento : String?  = null,
  @SerializedName("nombre"       ) var nombre       : String?  = null,
  @SerializedName("activo"       ) var activo       : Boolean? = null

)