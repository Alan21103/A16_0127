package com.example.pamfinal.model

import kotlinx.serialization.Serializable

data class PengeluaranResponse(
    val status:Boolean,
    val message: String,
    val data: List<Aset>
)

@Serializable
data class Pengeluaran(
    val id_pengeluaran: String,
    val id_aset: String,
    val id_kategori: String,
    val tanggal_transaksi: String,
    val total: String,
    val catatan: String
)