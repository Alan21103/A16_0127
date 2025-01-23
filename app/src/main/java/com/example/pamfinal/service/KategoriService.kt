package com.example.pamfinal.service

import com.example.pamfinal.model.Aset
import com.example.pamfinal.model.Kategori
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface KategoriService {
    @Headers(
        "Accept: applications/json",
        "Content: applications/json"
    )

    @GET("bacakategori.php")
    suspend fun getKategori(): List<Kategori>

    @GET("baca1kategori.php/{id_kategori}")
    suspend fun getKategoriById(@Query("id_kategori") id_kategori: String): Kategori

    @POST("insertkategori.php")
    suspend fun insertKategori(@Body kategori: Kategori)

    @PUT("editkategori.php/{id_kategori}")
    suspend fun updateKategori(@Query("id_kategori") id_kategori: String, @Body kategori: Kategori)

    @DELETE("deletekategori.php/{id_kategori}")
    suspend fun deleteKategori(@Query("id_kategori") id_kategori: String): Response<Void>
}