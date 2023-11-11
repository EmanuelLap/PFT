package com.example.pft.entidades

import com.google.gson.annotations.SerializedName


data class EventoDTO (

  @SerializedName("id"                                  ) var id                                  : Int?                                           = null,
  @SerializedName("titulo"                              ) var titulo                              : String?                                        = null,
  @SerializedName("tipoEvento"                          ) var tipoEvento                          : TipoEventoDTO?                                    = TipoEventoDTO(),
  @SerializedName("modalidadEvento"                     ) var modalidadEvento                     : ModalidadEventoDTO?                               = ModalidadEventoDTO(),
  @SerializedName("inicio"                              ) var inicio                              : Int?                                           = null,
  @SerializedName("fin"                                 ) var fin                                 : Int?                                           = null,
  @SerializedName("localizacion"                        ) var localizacion                        : String?                                        = null,
  @SerializedName("bajaLogica"                          ) var bajaLogica                          : Boolean?                                       = null,
  @SerializedName("itrDTO"                              ) var itrDTO                              : ItrDTO?                                        = ItrDTO(),
  @SerializedName("tutorResponsableEventoDTOCollection" ) var tutorResponsableEventoDTOCollection : ArrayList<TutorResponsableEventoDTO> = arrayListOf(),
  @SerializedName("tipoEstadoEvento"                    ) var tipoEstadoEvento                    : TipoEstadoEventoDTO?                              = TipoEstadoEventoDTO()

)