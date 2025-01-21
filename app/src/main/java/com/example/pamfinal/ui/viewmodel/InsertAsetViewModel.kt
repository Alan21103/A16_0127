package com.example.pamfinal.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.model.Aset
import com.example.pamfinal.repository.AsetRepository
import kotlinx.coroutines.launch

class InsertAsetViewModel(private val ast: AsetRepository) :ViewModel(){

    var uiState by mutableStateOf(InsertAsetUiState())
        private set

    fun updateInsertAstState(insertAsetUiEvent: InsertAsetUiEvent){
        uiState = InsertAsetUiState(insertAsetUiEvent = insertAsetUiEvent)
    }

    fun insertAset(){
        viewModelScope.launch {
            try {
                ast.insertAset(uiState.insertAsetUiEvent.toAst())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
data class InsertAsetUiState(
    val insertAsetUiEvent: InsertAsetUiEvent = InsertAsetUiEvent()
)

data class InsertAsetUiEvent(
    val nama_aset: String = ""
)

fun InsertAsetUiEvent.toAst(): Aset = Aset(
    id_aset = "",
    nama_aset = nama_aset
)

fun Aset.toUiStateAset(): InsertAsetUiState = InsertAsetUiState(
    insertAsetUiEvent = toInsertUiEvent()
)

fun Aset.toInsertUiEvent(): InsertAsetUiEvent = InsertAsetUiEvent(
    nama_aset = nama_aset
)
