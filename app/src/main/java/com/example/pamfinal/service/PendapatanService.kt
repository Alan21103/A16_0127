package com.example.pamfinal.service

import com.example.pamfinal.model.Pendapatan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PendapatanService {
    @Headers(
        "Accept: applications/json",
        "Content: applications/json"
    )

    @GET("bacapendapatan.php")
    suspend fun getPendapatan(): List<Pendapatan>

    @GET("totalpendapatan.php")
    suspend fun getTotalPendapatan(): Response<Map<String, Double>>

    @GET("totalpengeluaran.php")
    suspend fun getTotalPengeluaran(): Response<Map<String, Double>>

    @GET("baca1pendapatan.php/{id_pendapatan}")
    suspend fun getPendapatanById(@Query("id_pendapatan") id_pendapatan: String): Pendapatan

    @POST("insertpendapatan.php")
    suspend fun insertPendapatan(@Body pendapatan: Pendapatan)

    @PUT("editpendapatan.php/{id_pendapatan}")
    suspend fun updatePendapatan(@Query("id_pendapatan") id_pendapatan: String, @Body pendapatan: Pendapatan)

    @DELETE("deletependapatan.php/{id_pendapatan}")
    suspend fun deletePendapatan(@Query("id_pendapatan") id_pendapatan: String): Response<Void>
}