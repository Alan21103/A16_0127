package com.example.pamfinal.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.repository.KategoriRepository
import com.example.pamfinal.ui.view.kategori.DestinasiKategoriUpdate
import kotlinx.coroutines.launch

class UpdateKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository  : KategoriRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertKategoriUiState())
        private set

    val id_kategori: String = checkNotNull(savedStateHandle[DestinasiKategoriUpdate.id_kategori])

    init {
        viewModelScope.launch {
            uiState = kategoriRepository.getKategoriById(id_kategori).toUiStateKategori()
        }
    }

    fun updateKategoriState(insertKategoriUiEvent: InsertKategoriUiEvent) {
        uiState = InsertKategoriUiState(insertKategoriUiEvent = insertKategoriUiEvent)
    }

    suspend fun editKategori() {
        viewModelScope.launch {
            try {
                kategoriRepository.updateKategori(
                    id_kategori,
                    uiState.insertKategoriUiEvent.toKtg()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}