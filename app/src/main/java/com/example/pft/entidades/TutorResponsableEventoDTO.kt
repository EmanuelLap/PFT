package com.example.pft.entidades

import com.google.gson.annotations.SerializedName


data class TutorResponsableEventoDTO (

  @SerializedName("id"       ) var id       : Int?     = null,
  @SerializedName("tutorId"  ) var tutorId  : TutorDTO? = TutorDTO(),
  @SerializedName("eventoId" ) var eventoId : Int?     = null

)