package com.example.pft

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("ByteMinds_s4/rest/login/login")
    fun autenticarUsuario(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<Usuario>

}