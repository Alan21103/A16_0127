package com.example.pamfinal.model

import kotlinx.serialization.Serializable

data class KategoriResponse(
    val status:Boolean,
    val message: String,
    val data: List<Aset>
)

@Serializable
data class Kategori(
    val id_kategori: String,
    val nama_kategori: String,
)