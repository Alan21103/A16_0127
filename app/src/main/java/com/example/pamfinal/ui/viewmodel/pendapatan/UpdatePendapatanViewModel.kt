package com.example.pamfinal.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.repository.PendapatanRepository
import com.example.pamfinal.ui.view.pendapatan.DestinasiUpdatePendapatan
import kotlinx.coroutines.launch

class UpdatePendapatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val pendapatanRepository  : PendapatanRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPendapatanUiState())
        private set

    val id_pendapatan: String = checkNotNull(savedStateHandle[DestinasiUpdatePendapatan.id_pendapatan])

    init {
        viewModelScope.launch {
            uiState = pendapatanRepository.getPendapatanById(id_pendapatan).toUiStatePendapatan()
        }
    }

    fun updatePendapatanState(insertPendapatanUiEvent: InsertPendapatanUiEvent) {
        uiState = InsertPendapatanUiState(insertPendapatanUiEvent = insertPendapatanUiEvent)
    }

    suspend fun editPendapatan() {
        viewModelScope.launch {
            try {
                pendapatanRepository.updatePendapatan(
                    id_pendapatan,
                    uiState.insertPendapatanUiEvent.toPdt()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}