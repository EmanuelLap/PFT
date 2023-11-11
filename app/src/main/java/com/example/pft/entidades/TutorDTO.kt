package com.example.pft.entidades

import com.google.gson.annotations.SerializedName


data class TutorDTO (

  @SerializedName("id"              ) var id              : String?  = null,
  @SerializedName("documento"       ) var documento       : String?  = null,
  @SerializedName("usuario"         ) var usuario         : String?  = null,
  @SerializedName("contrasenia"     ) var contrasenia     : String?  = null,
  @SerializedName("apellidos"       ) var apellidos       : String?  = null,
  @SerializedName("nombres"         ) var nombres         : String?  = null,
  @SerializedName("fechaNacimiento" ) var fechaNacimiento : String?  = null,
  @SerializedName("departamento"    ) var departamento    : String?  = null,
  @SerializedName("genero"          ) var genero          : String?  = null,
  @SerializedName("localidad"       ) var localidad       : String?  = null,
  @SerializedName("mail"            ) var mail            : String?  = null,
  @SerializedName("mailPersonal"    ) var mailPersonal    : String?  = null,
  @SerializedName("telefono"        ) var telefono        : String?  = null,
  @SerializedName("itr"             ) var itr             : String?  = null,
  @SerializedName("rol"             ) var rol             : String?  = null,
  @SerializedName("activo"          ) var activo          : Boolean? = null,
  @SerializedName("validado"        ) var validado        : Boolean? = null,
  @SerializedName("tipoDTO"         ) var tipoDTO         : String?  = null,
  @SerializedName("areaDTO"         ) var areaDTO         : String?  = null,
  @SerializedName("utipo"           ) var utipo           : String?  = null

)