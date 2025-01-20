package com.example.pamfinal.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pamfinal.AsetApplications

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            HomeAsetViewModel(aplikasiKeuangan().container.asetRepository)
        }
    }
}

fun CreationExtras.aplikasiKeuangan(): AsetApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AsetApplications)