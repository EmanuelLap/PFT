package com.example.pft.entidades

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Itr(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("nombre") var nombre: String? = null,
    @SerializedName("departamento") var departamento: String? = null,
    @SerializedName("activo") var activo: Boolean? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(nombre)
        parcel.writeString(departamento)
        parcel.writeValue(activo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Itr> {
        override fun createFromParcel(parcel: Parcel): Itr {
            return Itr(parcel)
        }

        override fun newArray(size: Int): Array<Itr?> {
            return arrayOfNulls(size)
        }
    }
}
