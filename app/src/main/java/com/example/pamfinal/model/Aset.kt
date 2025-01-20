package com.example.pamfinal.model

import kotlinx.serialization.Serializable

@Serializable
data class AsetResponse(
    val status:Boolean,
    val message: String,
    val data: List<Aset>
)


@Serializable
data class Aset(
    val id_aset: String,
    val nama_aset: String
)