package com.example.pft

import com.example.pft.entidades.EstudianteDTO
import com.example.pft.entidades.Evento
import com.example.pft.entidades.EventoDTOMobile
import com.example.pft.entidades.Itr
import com.example.pft.entidades.LoginResponse
import com.example.pft.entidades.ModalidadEvento
import com.example.pft.entidades.ReclamoDTO
import com.example.pft.entidades.ReclamoDTOMobile
import com.example.pft.entidades.Rol
import com.example.pft.entidades.TipoAreaDTO
import com.example.pft.entidades.TipoEstadoEvento
import com.example.pft.entidades.TipoEvento
import com.example.pft.entidades.TipoTutorDTO
import com.example.pft.entidades.TutorDTO
import com.example.pft.entidades.UsuarioDTO
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

    //Usuarios
    @GET("ByteMinds_s4/rest/usuarios/listar")
    fun obtenerUsuarios(): Call<List<Usuario>>

    @GET("ByteMinds_s4/rest/usuarios/listarTutoresActivos")
    fun obtenerTutoresActivos(): Call<List<Usuario>>

    @POST("ByteMinds_s4/rest/usuarios/agregarJson")
    fun agregarUsuario(
        @Body usuario: UsuarioDTO
    ): Call<UsuarioDTO>

    @POST("ByteMinds_s4/rest/usuarios/agregarJson")
    fun agregarUsuarioEstudiante(
        @Body usuario: EstudianteDTO
    ): Call<EstudianteDTO>

    @POST("ByteMinds_s4/rest/usuarios/agregarJson")
    fun agregarUsuarioTutor(
        @Body usuario: TutorDTO
    ): Call<TutorDTO>

    @POST("ByteMinds_s4/rest/usuarios/eliminarUsuarioJson")
    fun eliminarUsuario(
        @Body usuario: UsuarioDTO
    ): Call<UsuarioDTO>

    //Eventos
    @GET("ByteMinds_s4/rest/eventos/listar")
    fun obtenerEventos(): Call<List<Evento>>

    @GET("ByteMinds_s4/rest/tipoevento/listar")
    fun obtenerTipos(): Call<List<TipoEvento>>

    @GET("ByteMinds_s4/rest/modalidadevento/listar")
    fun obtenerModalidades(): Call<List<ModalidadEvento>>

    @GET("ByteMinds_s4/rest/tipoestadoevento/listar")
    fun obtenerTipoEstados(): Call<List<TipoEstadoEvento>>

    @POST("ByteMinds_s4/rest/eventos/agregarJsonMobile")
    fun agregarEvento(
        @Body evento: EventoDTOMobile
    ): Call<EventoDTOMobile>


    @POST("ByteMinds_s4/rest/eventos/modificarEventoJsonMobile")
    fun modificarEvento(
        @Body evento: EventoDTOMobile
    ): Call<EventoDTOMobile>

    @POST("ByteMinds_s4/rest/eventos/eliminarEventoJsonMobile")
    fun eliminarEvento(
        @Body evento: EventoDTOMobile
    ): Call<EventoDTOMobile>

    //Reclamos
    @GET("ByteMinds_s4/rest/reclamos/listar")
    fun obtenerReclamos(): Call<List<ReclamoDTO>>

    @GET("ByteMinds_s4/rest/reclamos/obtenerEjemploJsonMobile")
    fun obtenerReclamosMobile(): Call<List<ReclamoDTOMobile>>


    @POST("ByteMinds_s4/rest/reclamos/agregarJsonMobile")
    fun agregarReclamo(
        @Body reclamo: ReclamoDTOMobile
    ): Call<ReclamoDTOMobile>

    @POST("ByteMinds_s4/rest/reclamos/modificarReclamoJsonMobile")
    fun modificarReclamo(
        @Body reclamo: ReclamoDTOMobile
    ): Call<ReclamoDTOMobile>

    @POST("ByteMinds_s4/rest/reclamos/eliminarReclamoJsonMobile")
    fun eliminarReclamo(
        @Body reclamo: ReclamoDTOMobile
    ): Call<ReclamoDTOMobile>

    //ITR
    @GET("ByteMinds_s4/rest/itrs/listar")
    fun obtenerITR(): Call<List<Itr>>

    //ROLES
    @GET("ByteMinds_s4/rest/rol/listar")
    fun obtenerRoles(): Call<List<Rol>>

    //TIPOS TUTOR
    @GET("ByteMinds_s4/rest/tipotutor/listar")
    fun obtenerTiposTutor(): Call<List<TipoTutorDTO>>

    //AREAS
    @GET("ByteMinds_s4/rest/area/listar")
    fun obtenerAreas(): Call<List<TipoAreaDTO>>
}