package com.example.pamfinal.repository


import android.util.Log
import com.example.pamfinal.model.Pendapatan
import com.example.pamfinal.service.PendapatanService
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException

interface PendapatanRepository{
    suspend fun getPendapatan(): List<Pendapatan>
    suspend fun insertPendapatan(pendapatan: Pendapatan)
    suspend fun updatePendapatan(id_pendapatan: String, pendapatan: Pendapatan)
    suspend fun deletePendapatan(id_pendapatan: String)
    suspend fun getPendapatanById(id_pendapatan: String): Pendapatan
    suspend fun getTotalPendapatan(): Double
    suspend fun getTotalPengeluaran(): Double
}

class NetworkPendapatanRepository(
    private val pendapatanApiService: PendapatanService
): PendapatanRepository{
    override suspend fun insertPendapatan(pendapatan: Pendapatan) {
        pendapatanApiService.insertPendapatan(pendapatan)
    }

    override suspend fun updatePendapatan(id_pendapatan: String, pendapatan: Pendapatan) {
        pendapatanApiService.updatePendapatan(id_pendapatan, pendapatan)
    }

    override suspend fun deletePendapatan(id_pendapatan: String) {
        try {
            val response = pendapatanApiService.deletePendapatan(id_pendapatan)
            if (!response.isSuccessful){
                throw IOException("Failed to delete aset. HTTP Status code: ${response.code()}")
            }else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun getPendapatan(): List<Pendapatan> = pendapatanApiService.getPendapatan()

    override suspend fun getPendapatanById(id_pendapatan: String): Pendapatan {
        try {
            return pendapatanApiService.getPendapatanById(id_pendapatan)
        } catch (e: IOException){
            throw IOException("Failed to fetch aset with ID Aset: $id_pendapatan. Network error occurred.", e)
        }
    }

    override suspend fun getTotalPendapatan(): Double {
        return try {
            val response = pendapatanApiService.getTotalPendapatan()

            if (response.isSuccessful) {
                val totalPendapatan = response.body()?.get("total")?.toString()?.toDoubleOrNull() ?: 0.0
                totalPendapatan
            } else {
                val errorBody = response.errorBody()?.string() // Menampilkan error body jika gagal
                throw IOException("Gagal mendapatkan total pendapatan. HTTP Status code: ${response.code()}. Error body: $errorBody")
            }
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTPException: ${e.message()}")
            throw IOException(
                "Gagal mendapatkan total pendapatan. HTTP Status code: ${e.code()}",
            )
        }
    }


    override suspend fun getTotalPengeluaran(): Double {
        return try {
            val response = pendapatanApiService.getTotalPengeluaran()
            if (response.isSuccessful) {
                val totalPengeluaran = response.body()?.get("total")?.toString()?.toDoubleOrNull() ?: 0.0
                totalPengeluaran
            } else {
                throw IOException("Gagal mendapatkan total pengeluaran. HTTP Status code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw IOException("Gagal mendapatkan total pengeluaran. Error jaringan.", e)
        }
    }

}