package com.example.pamfinal.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.model.Pengeluaran
import com.example.pamfinal.repository.PengeluaranRepository
import kotlinx.coroutines.launch

class HomePengeluaranViewModel(private val pgl: PengeluaranRepository) : ViewModel(){

    var uiState by mutableStateOf(InsertPengeluaranUiState())
        private set

    fun updateInsertPglState(insertPengeluaranUiEvent: InsertPengeluaranUiEvent){
        uiState = InsertPengeluaranUiState(insertPengeluaranUiEvent = insertPengeluaranUiEvent)
    }

    fun insertPengeluaran(){
        viewModelScope.launch {
            try {
                pgl.insertPengeluaran(uiState.insertPengeluaranUiEvent.toPgl())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
data class InsertPengeluaranUiState(
    val insertPengeluaranUiEvent: InsertPengeluaranUiEvent = InsertPengeluaranUiEvent()
)

data class InsertPengeluaranUiEvent(
    val id_pengeluaran: String = "",
    val id_aset: String = "",
    val id_kategori: String = "",
    val tanggal_transaksi: String = "",
    val total: String = "",
    val catatan: String = ""
)

fun InsertPengeluaranUiEvent.toPgl(): Pengeluaran = Pengeluaran(
    id_pengeluaran = id_pengeluaran,
    id_aset = id_aset,
    id_kategori = id_kategori,
    tanggal_transaksi = tanggal_transaksi,
    total = total,
    catatan = catatan
)

fun Pengeluaran.toUiStatePengeluaran(): InsertPengeluaranUiState = InsertPengeluaranUiState(
    insertPengeluaranUiEvent = toInsertUiEvent()
)

fun Pengeluaran.toInsertUiEvent(): InsertPengeluaranUiEvent = InsertPengeluaranUiEvent(
    id_aset = id_aset,
    id_kategori = id_kategori,
    tanggal_transaksi = tanggal_transaksi,
    total = total,
    catatan = catatan
)