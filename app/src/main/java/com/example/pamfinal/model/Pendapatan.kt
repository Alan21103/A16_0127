package com.example.pamfinal.model

import kotlinx.serialization.Serializable

data class PendapatanResponse(
    val status:Boolean,
    val message: String,
    val data: List<Aset>
)

@Serializable
data class Pendapatan(
    val id_pendapatan: String,
    val id_aset: String,
    val id_kategori: String,
    val tanggal_transaksi: String,
    val total: String,
    val catatan: String
)