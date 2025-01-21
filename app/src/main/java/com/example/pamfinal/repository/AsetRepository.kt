package com.example.pamfinal.repository

import com.example.pamfinal.model.Aset
import com.example.pamfinal.service.AsetService
import okio.IOException

interface AsetRepository{
    suspend fun getAset(): List<Aset>
    suspend fun insertAset(aset: Aset)
    suspend fun updateAset(id_aset: String, aset: Aset)
    suspend fun deleteAset(id_aset: String)
    suspend fun getAsetById(id_aset: String): Aset
}

class NetworkAsetRepository(
    private val asetApiService: AsetService
): AsetRepository{
    override suspend fun insertAset(aset: Aset) {
        asetApiService.insertAset(aset)
    }

    override suspend fun updateAset(id_aset: String, aset: Aset) {
        asetApiService.updateAset(id_aset, aset)
    }

    override suspend fun deleteAset(id_aset: String) {
        try {
            val response = asetApiService.deleteAset(id_aset)
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

    override suspend fun getAset(): List<Aset> = asetApiService.getAset()

    override suspend fun getAsetById(id_aset: String): Aset {
        try {
            return asetApiService.getAsetById(id_aset)
        } catch (e: IOException){
            throw IOException("Failed to fetch aset with ID Aset: $id_aset. Network error occurred.", e)
        }

    }

}