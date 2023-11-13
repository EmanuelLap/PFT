package com.example.pft

import com.example.pft.entidades.Evento
import com.example.pft.entidades.LoginResponse
import com.example.pft.ui.login.Credenciales
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

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

    @GET("ByteMinds_s4/rest/eventos/listar")
    fun obtenerEventos(): Call<List<Evento>>

}