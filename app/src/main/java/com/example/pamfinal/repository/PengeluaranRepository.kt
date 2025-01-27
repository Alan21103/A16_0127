package com.example.pamfinal.repository

import com.example.pamfinal.model.Pengeluaran
import com.example.pamfinal.service.PengeluaranService
import okio.IOException

interface PengeluaranRepository{
    suspend fun getPengeluaran(): List<Pengeluaran>
    suspend fun insertPengeluaran(pengeluaran: Pengeluaran)
    suspend fun updatePengeluaran(id_pengeluaran: String, pengeluaran: Pengeluaran)
    suspend fun deletePengeluaran(id_pengeluaran: String)
    suspend fun getPengeluaranById(id_pengeluaran: String): Pengeluaran
}

class NetworkPengeluaranRepository(
    private val pengeluaranApiService: PengeluaranService
): PengeluaranRepository{
    override suspend fun insertPengeluaran(pengeluaran: Pengeluaran) {
        pengeluaranApiService.insertPengeluaran(pengeluaran)
    }

    override suspend fun updatePengeluaran(id_pengeluaran: String, pengeluaran: Pengeluaran) {
        pengeluaranApiService.updatePengeluaran(id_pengeluaran, pengeluaran)
    }

    override suspend fun deletePengeluaran(id_pengeluaran: String) {
        try {
            val response = pengeluaranApiService.deletePengeluaran(id_pengeluaran)
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

    override suspend fun getPengeluaran(): List<Pengeluaran> = pengeluaranApiService.getPengeluaran()

    override suspend fun getPengeluaranById(id_pengeluaran: String): Pengeluaran {
        try {
            return pengeluaranApiService.getPengeluaranById(id_pengeluaran)
        } catch (e: IOException){
            throw IOException("Failed to fetch aset with ID Aset: $id_pengeluaran. Network error occurred.", e)
        }

    }


}