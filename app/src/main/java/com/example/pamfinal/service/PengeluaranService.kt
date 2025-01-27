package com.example.pamfinal.service

import com.example.pamfinal.model.Pengeluaran
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PengeluaranService {
    @Headers(
        "Accept: applications/json",
        "Content: applications/json"
    )

    @GET("bacapengeluaran.php")
    suspend fun getPengeluaran(): List<Pengeluaran>

    @GET("baca1pengeluaran.php/{id_pengeluaran}")
    suspend fun getPengeluaranById(@Query("id_pengeluaran") id_pengeluaran: String): Pengeluaran

    @POST("insertpengeluaran.php")
    suspend fun insertPengeluaran(@Body pengeluaran: Pengeluaran)

    @PUT("editpengeluaran.php/{id_pengeluaran}")
    suspend fun updatePengeluaran(@Query("id_pengeluaran") id_pengeluaran: String, @Body pengeluaran: Pengeluaran)

    @DELETE("deletepengeluaran.php/{id_pengeluaran}")
    suspend fun deletePengeluaran(@Query("id_pengeluaran") id_pengeluaran: String): Response<Void>
}