package com.example.pft.entidades


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Rol(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("nombre") var nombre: String? = null,
    @SerializedName("descripcion") var descripcion: String? = null,
    @SerializedName("activo") var activo: Boolean? = null,
    @SerializedName("funcionalidades") var funcionalidades: List<Funcionalidades> = arrayListOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.createTypedArrayList(Funcionalidades.CREATOR) ?: arrayListOf()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
        parcel.writeValue(activo)
        parcel.writeTypedList(funcionalidades)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rol> {
        override fun createFromParcel(parcel: Parcel): Rol {
            return Rol(parcel)
        }

        override fun newArray(size: Int): Array<Rol?> {
            return arrayOfNulls(size)
        }
    }
}
