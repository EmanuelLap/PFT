package com.example.pft.entidades

import android.os.Parcel
import android.os.Parcelable

data class UsuarioDTO(
    val activo: Boolean,
    val apellidos: String,
    val contrasenia: String,
    val departamento: String,
    val documento: Int,
    val fechaNacimiento: Long,
    val genero: String,
    val id: Int?,
    val itr: Itr,
    val localidad: Any?,
    val mail: String,
    val mailPersonal: Any?,
    val nombres: String,
    val rol: Rol,
    val telefono: String,
    val usuario: String,
    val utipo: String,
    val validado: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Itr::class.java.classLoader) ?: Itr(),
        parcel.readValue(Any::class.java.classLoader), // Lee cualquier tipo de objeto
        parcel.readString() ?: "",
        parcel.readValue(Any::class.java.classLoader), // Lee cualquier tipo de objeto
        parcel.readString() ?: "",
        parcel.readParcelable(Rol::class.java.classLoader) ?: Rol(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (activo) 1 else 0)
        parcel.writeString(apellidos)
        parcel.writeString(contrasenia)
        parcel.writeString(departamento)
        parcel.writeInt(documento)
        parcel.writeLong(fechaNacimiento)
        parcel.writeString(genero)
        parcel.writeValue(id)
        parcel.writeParcelable(itr, flags)
        parcel.writeValue(localidad) // Escribe cualquier tipo de objeto
        parcel.writeString(mail)
        parcel.writeValue(mailPersonal) // Escribe cualquier tipo de objeto
        parcel.writeString(nombres)
        parcel.writeParcelable(rol, flags)
        parcel.writeString(telefono)
        parcel.writeString(usuario)
        parcel.writeString(utipo)
        parcel.writeByte(if (validado) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsuarioDTO> {
        override fun createFromParcel(parcel: Parcel): UsuarioDTO {
            return UsuarioDTO(parcel)
        }

        override fun newArray(size: Int): Array<UsuarioDTO?> {
            return arrayOfNulls(size)
        }
    }
}
