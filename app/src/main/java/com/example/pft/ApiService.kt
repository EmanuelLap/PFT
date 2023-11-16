package com.example.pft

import com.example.pft.entidades.EstudianteId
import com.example.pft.entidades.Evento
import com.example.pft.entidades.EventoId
import com.example.pft.entidades.LoginResponse
import com.example.pft.entidades.Reclamo
import com.example.pft.entidades.ReclamoResponse
import com.example.pft.ui.login.Credenciales
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    //Login
    @FormUrlEncoded
    @POST("ByteMinds_s4/rest/login/login")
    fun autenticarUsuario(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<Usuario>

    @POST("ByteMinds_s4/rest/login/login")
    fun login(
        @Body credentials: Credenciales
    ): Call<LoginResponse>

    //Eventos
    @GET("ByteMinds_s4/rest/eventos/listar")
    fun obtenerEventos(): Call<List<Evento>>

    //Reclamos
    @GET("ByteMinds_s4/rest/reclamos/listar")
    fun obtenerReclamos(): Call<List<Reclamo>>

    @FormUrlEncoded
    @POST("ByteMinds_s4/rest/reclamos/agregarJson")
    fun agregarReclamo(
        @Field("activo") activo: Any?,
        @Field("creditos") creditos: Int,
        @Field("detalle")detalle: String,
        @Field("estudianteId") estudianteId: EstudianteId,
        @Field ("eventoID") eventoId: EventoId,
        @Field ("fecha") fecha: Long,
        @Field ("semestre") semestre: Int,
        @Field ("titulo") titulo: String
    ): Call<ReclamoResponse>

}