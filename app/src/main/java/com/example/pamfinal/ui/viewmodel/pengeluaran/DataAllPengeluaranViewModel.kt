package com.example.pamfinal.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.model.Pengeluaran
import com.example.pamfinal.repository.PengeluaranRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DataAllPengeluaranUiState{
    data class Success(val pengeluaran: List<Pengeluaran>): DataAllPengeluaranUiState()
    object Error : DataAllPengeluaranUiState()
    object Loading : DataAllPengeluaranUiState()
}

class DataAllPengeluaranViewModel(private val pgl: PengeluaranRepository): ViewModel(){

    var pglUIState: DataAllPengeluaranUiState by mutableStateOf(DataAllPengeluaranUiState.Loading)
        private set

    init {
        getPgl()
    }

    fun getPgl() {
        viewModelScope.launch {
            pglUIState = DataAllPengeluaranUiState.Loading
            pglUIState = try {
                val pengeluaranList = pgl.getPengeluaran()
                DataAllPengeluaranUiState.Success(pengeluaranList)
            } catch (e: IOException) {
                DataAllPengeluaranUiState.Error
            } catch (e: HttpException) {
                DataAllPengeluaranUiState.Error
            }
        }
    }
}