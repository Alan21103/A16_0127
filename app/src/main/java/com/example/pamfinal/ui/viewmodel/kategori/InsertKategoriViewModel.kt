package com.example.pamfinal.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.model.Kategori
import com.example.pamfinal.repository.KategoriRepository
import kotlinx.coroutines.launch

class InsertKategoriViewModel(private val ktg: KategoriRepository) : ViewModel(){

    var uiState by mutableStateOf(InsertKategoriUiState())
        private set

    fun updateInsertKtgState(insertKategoriUiEvent: InsertKategoriUiEvent){
        uiState = InsertKategoriUiState(insertKategoriUiEvent = insertKategoriUiEvent)
    }

    fun insertKategori(){
        viewModelScope.launch {
            try {
                ktg.    insertKategori(uiState.insertKategoriUiEvent.toKtg())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
data class InsertKategoriUiState(
    val insertKategoriUiEvent: InsertKategoriUiEvent = InsertKategoriUiEvent()
)

data class InsertKategoriUiEvent(
    val nama_kategori: String = ""
)

fun InsertKategoriUiEvent.toKtg(): Kategori = Kategori(
    id_kategori = "",
    nama_kategori = nama_kategori
)

fun Kategori.toUiStateKategori(): InsertKategoriUiState = InsertKategoriUiState(
    insertKategoriUiEvent = toInsertUiEvent()
)

fun Kategori.toInsertUiEvent(): InsertKategoriUiEvent = InsertKategoriUiEvent(
    nama_kategori = nama_kategori
)