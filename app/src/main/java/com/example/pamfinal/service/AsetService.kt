package com.example.pamfinal.service

import com.example.pamfinal.model.Aset
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AsetService {
    @Headers(
        "Accept: applications/json",
        "Content: applications/json"
    )

    @GET("bacaaset.php")
    suspend fun getAset(): List<Aset>

    @POST("insertaset.php")
    suspend fun insertAset(@Body aset: Aset)

    @PUT("editaset.php/{id_aset}")
    suspend fun updateAset(@Query("id_aset") id_aset: String, @Body aset: Aset)

    @DELETE("deleteaset.php/{id_aset}")
    suspend fun deleteAset(@Query("id_aset") id_aset: String): Response<Void>
}