package com.example.pamfinal.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.model.Pendapatan
import com.example.pamfinal.repository.PendapatanRepository
import kotlinx.coroutines.launch

class HomePendapatanViewModel(private val pdt: PendapatanRepository) : ViewModel(){

    var uiState by mutableStateOf(InsertPendapatanUiState())
        private set

    fun updateInsertPdtState(insertPendapatanUiEvent: InsertPendapatanUiEvent){
        uiState = InsertPendapatanUiState(insertPendapatanUiEvent = insertPendapatanUiEvent)
    }

    fun insertPendapatan(){
        viewModelScope.launch {
            try {
                pdt.    insertPendapatan(uiState.insertPendapatanUiEvent.toPdt())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
data class InsertPendapatanUiState(
    val insertPendapatanUiEvent: InsertPendapatanUiEvent = InsertPendapatanUiEvent()
)

data class InsertPendapatanUiEvent(
    val id_pendapatan: String = "",
    val id_aset: String = "",
    val id_kategori: String = "",
    val tanggal_transaksi: String = "",
    val total: String = "",
    val catatan: String = ""
)

fun InsertPendapatanUiEvent.toPdt(): Pendapatan = Pendapatan(
    id_pendapatan = id_pendapatan,
    id_aset = id_aset,
    id_kategori = id_kategori,
    tanggal_transaksi = tanggal_transaksi,
    total = total,
    catatan = catatan
)

fun Pendapatan.toUiStatePendapatan(): InsertPendapatanUiState = InsertPendapatanUiState(
    insertPendapatanUiEvent = toInsertUiEvent()
)

fun Pendapatan.toInsertUiEvent(): InsertPendapatanUiEvent = InsertPendapatanUiEvent(
    id_aset = id_aset,
    id_kategori = id_kategori,
    tanggal_transaksi = tanggal_transaksi,
    total = total,
    catatan = catatan
)