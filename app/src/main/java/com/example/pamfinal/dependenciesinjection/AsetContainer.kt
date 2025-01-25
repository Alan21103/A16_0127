package com.example.pamfinal.dependenciesinjection

import com.example.pamfinal.repository.AsetRepository
import com.example.pamfinal.repository.KategoriRepository
import com.example.pamfinal.repository.NetworkAsetRepository
import com.example.pamfinal.repository.NetworkKategoriRepository
import com.example.pamfinal.repository.NetworkPendapatanRepository
import com.example.pamfinal.repository.NetworkPengeluaranRepository
import com.example.pamfinal.repository.PendapatanRepository
import com.example.pamfinal.repository.PengeluaranRepository
import com.example.pamfinal.service.AsetService
import com.example.pamfinal.service.KategoriService
import com.example.pamfinal.service.PendapatanService
import com.example.pamfinal.service.PengeluaranService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val asetRepository: AsetRepository
    val kategoriRepository: KategoriRepository
    val pendapatanRepository: PendapatanRepository
    val pengeluaranRepository: PengeluaranRepository
}

class AsetContainer: AppContainer{
    private val baseUrl = "http://10.0.2.2/BackendPAM/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val asetService: AsetService by lazy { retrofit.create(AsetService::class.java) }
    override val asetRepository: AsetRepository by lazy { NetworkAsetRepository(asetService) }

    private val kategoriService: KategoriService by lazy { retrofit.create((KategoriService::class.java)) }
    override val kategoriRepository: KategoriRepository by lazy { NetworkKategoriRepository(kategoriService ) }

    private val pendapatanService: PendapatanService by lazy { retrofit.create((PendapatanService::class.java)) }
    override val pendapatanRepository: PendapatanRepository by lazy { NetworkPendapatanRepository(pendapatanService) }

    private val pengeluaranService: PengeluaranService by lazy { retrofit.create((PengeluaranService::class.java)) }
    override val pengeluaranRepository: PengeluaranRepository by lazy { NetworkPengeluaranRepository(pengeluaranService) }
}