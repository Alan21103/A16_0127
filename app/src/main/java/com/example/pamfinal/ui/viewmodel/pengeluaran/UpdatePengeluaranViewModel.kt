package com.example.pamfinal.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.repository.PengeluaranRepository
import com.example.pamfinal.ui.view.pengeluaran.DestinasiUpdatePengeluaran
import kotlinx.coroutines.launch

class UpdatePengeluaranViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengeluaranRepository  : PengeluaranRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPengeluaranUiState())
        private set

    val id_pengeluaran: String = checkNotNull(savedStateHandle[DestinasiUpdatePengeluaran.id_pengeluaran])

    init {
        viewModelScope.launch {
            uiState = pengeluaranRepository.getPengeluaranById(id_pengeluaran).toUiStatePengeluaran()
        }
    }

    fun updatePengeluaranState(insertPengeluaranUiEvent: InsertPengeluaranUiEvent) {
        uiState = InsertPengeluaranUiState(insertPengeluaranUiEvent = insertPengeluaranUiEvent)
    }

    suspend fun editPengeluaran() {
        viewModelScope.launch {
            try {
                pengeluaranRepository.updatePengeluaran(
                    id_pengeluaran,
                    uiState.insertPengeluaranUiEvent.toPgl()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}