package com.example.pft.entidades


import com.google.gson.annotations.SerializedName


data class User (

    @SerializedName("id"              ) var id              : Int?     = null,
    @SerializedName("documento"       ) var documento       : Int?     = null,
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
    @SerializedName("itr"             ) var itr             : Itr?     = Itr(),
    @SerializedName("rol"             ) var rol             : Rol?     = Rol(),
    @SerializedName("activo"          ) var activo          : Boolean? = null,
    @SerializedName("validado"        ) var validado        : Boolean? = null,
    @SerializedName("utipo"           ) var utipo           : String?  = null

)