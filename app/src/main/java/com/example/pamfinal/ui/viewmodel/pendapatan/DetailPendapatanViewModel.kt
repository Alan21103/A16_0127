package com.example.pamfinal.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.model.Pendapatan
import com.example.pamfinal.repository.PendapatanRepository
import com.example.pamfinal.ui.view.pendapatan.DestinasiDetailPendapatan
import kotlinx.coroutines.launch

class DetailPendapatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val pdt: PendapatanRepository
) : ViewModel() {
    private val id_pendapatan: String = checkNotNull(savedStateHandle[DestinasiDetailPendapatan.id_pendapatan])

    var detailUiState: DetailPendapatanUiState by mutableStateOf(DetailPendapatanUiState())
        private set

    var isDeleted: Boolean by mutableStateOf(false) // Status apakah data berhasil dihapus
        private set

    init {
        getPendapatanById()
    }

    fun getPendapatanById() {
        viewModelScope.launch {
            detailUiState = DetailPendapatanUiState(isLoading = true)
            try {
                val result = pdt.getPendapatanById(id_pendapatan)
                detailUiState = DetailPendapatanUiState(
                    detailUiEvent = result.toDetailPendapatanUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailUiState = DetailPendapatanUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun deletePendapatan() {
        viewModelScope.launch {
            try {
                pdt.deletePendapatan(id_pendapatan) // Memanggil fungsi di repository
                isDeleted = true // Tandai penghapusan berhasil
            } catch (e: Exception) {
                detailUiState = detailUiState.copy(
                    isError = true,
                    errorMessage = e.message ?: "Gagal menghapus data"
                )
            }
        }
    }
}


data class DetailPendapatanUiState(
    val detailUiEvent: InsertPendapatanUiEvent = InsertPendapatanUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == InsertPendapatanUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertPendapatanUiEvent()
}

fun Pendapatan.toDetailPendapatanUiEvent(): InsertPendapatanUiEvent{
    return InsertPendapatanUiEvent(
        id_pendapatan = id_pendapatan,
        id_aset = id_aset,
        id_kategori = id_kategori,
        tanggal_transaksi = tanggal_transaksi,
        total = total,
        catatan = catatan
    )
}