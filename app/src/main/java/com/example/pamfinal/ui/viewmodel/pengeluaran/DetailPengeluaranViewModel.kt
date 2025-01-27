package com.example.pamfinal.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.model.Pengeluaran
import com.example.pamfinal.repository.PengeluaranRepository
import com.example.pamfinal.ui.view.pengeluaran.DestinasiDetailPengeluaran
import kotlinx.coroutines.launch

class DetailPengeluaranViewModel(
    savedStateHandle: SavedStateHandle,
    private val pgl: PengeluaranRepository
) : ViewModel() {
    private val id_pengeluaran: String = checkNotNull(savedStateHandle[DestinasiDetailPengeluaran.id_pengeluaran])

    var detailUiState: DetailPengeluaranUiState by mutableStateOf(DetailPengeluaranUiState())
        private set

    var isDeleted: Boolean by mutableStateOf(false) // Status apakah data berhasil dihapus
        private set

    init {
        getPengeluaranById()
    }

    fun getPengeluaranById() {
        viewModelScope.launch {
            detailUiState = DetailPengeluaranUiState(isLoading = true)
            try {
                val result = pgl.getPengeluaranById(id_pengeluaran)
                detailUiState = DetailPengeluaranUiState(
                    detailUiEvent = result.toDetailPengeluaranUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailUiState = DetailPengeluaranUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun deletePengeluaran() {
        viewModelScope.launch {
            try {
                pgl.deletePengeluaran(id_pengeluaran) // Memanggil fungsi di repository
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


data class DetailPengeluaranUiState(
    val detailUiEvent: InsertPengeluaranUiEvent = InsertPengeluaranUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == InsertPengeluaranUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertPengeluaranUiEvent()
}

fun Pengeluaran.toDetailPengeluaranUiEvent(): InsertPengeluaranUiEvent {
    return InsertPengeluaranUiEvent(
        id_pengeluaran = id_pengeluaran,
        id_aset = id_aset,
        id_kategori = id_kategori,
        tanggal_transaksi = tanggal_transaksi,
        total = total,
        catatan = catatan
    )
}