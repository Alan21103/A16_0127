package com.example.pamfinal.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.repository.AsetRepository
import com.example.pamfinal.ui.view.DestinasiAsetUpdate
import kotlinx.coroutines.launch

class UpdateAsetViewModel(
    savedStateHandle: SavedStateHandle,
    private val asetRepository  : AsetRepository
) : ViewModel(){

    var uiState by mutableStateOf(InsertAsetUiState())
        private set

    val id_aset: String = checkNotNull(savedStateHandle[DestinasiAsetUpdate.id_aset])

    init {
        viewModelScope.launch {
            uiState = asetRepository.getAsetById(id_aset).toUiStateAset()
        }
    }

    fun updateAsetState(insertAsetUiEvent: InsertAsetUiEvent){
        uiState = InsertAsetUiState(insertAsetUiEvent = insertAsetUiEvent)
    }

    suspend fun editAset(){
        viewModelScope.launch {
            try {
                asetRepository.updateAset(id_aset, uiState.insertAsetUiEvent.toAst())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
