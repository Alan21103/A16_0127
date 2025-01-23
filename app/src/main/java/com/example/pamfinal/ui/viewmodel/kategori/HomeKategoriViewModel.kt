package com.example.pamfinal.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamfinal.model.Kategori
import com.example.pamfinal.repository.KategoriRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeKategoriUiState{
    data class Success(val kategori: List<Kategori>): HomeKategoriUiState()
    object Error : HomeKategoriUiState()
    object Loading : HomeKategoriUiState()
}

class HomeKategoriViewModel(private val ktg: KategoriRepository): ViewModel(){

    var ktgUIState: HomeKategoriUiState by mutableStateOf(HomeKategoriUiState.Loading)
        private set

    init {
        getKtg()
    }

    fun    getKtg(){
        viewModelScope.launch {
            ktgUIState = HomeKategoriUiState.Loading
            ktgUIState = try {
                HomeKategoriUiState.Success(ktg.getKategori())
            } catch (e: IOException){
                HomeKategoriUiState.Error
            }catch (e: HttpException){
                HomeKategoriUiState.Error
            }
        }
    }

    fun deleteKtg(id_kategori: String) {
        viewModelScope.launch {
            try {
                ktg.deleteKategori(id_kategori)
            } catch (e: IOException) {
                ktgUIState = HomeKategoriUiState.Error
            } catch (e: HttpException) {
                ktgUIState = HomeKategoriUiState.Error
            }
        }
    }
}