package com.example.pamfinal.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.model.Aset
import com.example.pamfinal.repository.AsetRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeAsetUiState{
    data class Success(val aset: List<Aset>): HomeAsetUiState()
    object Error : HomeAsetUiState()
    object Loading : HomeAsetUiState()
}

class HomeAsetViewModel(private val ast: AsetRepository): ViewModel(){

    var astUIState: HomeAsetUiState by mutableStateOf(HomeAsetUiState.Loading)
        private set

    init {
        getAst()
    }

    fun getAst(){
        viewModelScope.launch {
            astUIState = HomeAsetUiState.Loading
            astUIState = try {
                HomeAsetUiState.Success(ast.getAset())
            } catch (e: IOException){
                HomeAsetUiState.Error
            }catch (e: HttpException){
                HomeAsetUiState.Error
            }
        }
    }

    fun deleteAst(id_aset: String) {
        viewModelScope.launch {
            try {
                ast.deleteAset(id_aset)
            } catch (e: IOException) {
                astUIState = HomeAsetUiState.Error
            } catch (e: HttpException) {
                astUIState = HomeAsetUiState.Error
            }
        }
    }
}