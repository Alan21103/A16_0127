package com.example.pamfinal.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.model.Pendapatan
import com.example.pamfinal.repository.PendapatanRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DataAllPendapatanUiState{
    data class Success(val pendapatan: List<Pendapatan>): DataAllPendapatanUiState()
    object Error : DataAllPendapatanUiState()
    object Loading : DataAllPendapatanUiState()
}

class DataAllPendapatanViewModel(private val pdt: PendapatanRepository): ViewModel(){

    var pdtUIState: DataAllPendapatanUiState by mutableStateOf(DataAllPendapatanUiState.Loading)
        private set

    init {
        getPdt()
    }

    fun getPdt() {
        viewModelScope.launch {
            pdtUIState = DataAllPendapatanUiState.Loading
            pdtUIState = try {
                val pendapatanList = pdt.getPendapatan()
                DataAllPendapatanUiState.Success(pendapatanList)
            } catch (e: IOException) {
                DataAllPendapatanUiState.Error
            } catch (e: HttpException) {
                DataAllPendapatanUiState.Error
            }
        }
    }
}