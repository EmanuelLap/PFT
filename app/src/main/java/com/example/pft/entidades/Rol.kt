package com.example.pft.entidades


import com.google.gson.annotations.SerializedName

data class Rol (

    @SerializedName("id"              ) var id              : Int?                       = null,
    @SerializedName("nombre"          ) var nombre          : String?                    = null,
    @SerializedName("descripcion"     ) var descripcion     : String?                    = null,
    @SerializedName("activo"          ) var activo          : Boolean?                   = null,
    @SerializedName("funcionalidades" ) var funcionalidades : ArrayList<Funcionalidades> = arrayListOf()

)