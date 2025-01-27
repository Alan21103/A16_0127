package com.example.pamfinal.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pamfinal.AsetApplications
import com.example.pamfinal.ui.viewmodel.aset.HomeAsetViewModel
import com.example.pamfinal.ui.viewmodel.aset.InsertAsetViewModel
import com.example.pamfinal.ui.viewmodel.aset.UpdateAsetViewModel
import com.example.pamfinal.ui.viewmodel.kategori.HomeKategoriViewModel
import com.example.pamfinal.ui.viewmodel.kategori.InsertKategoriViewModel
import com.example.pamfinal.ui.viewmodel.kategori.UpdateKategoriViewModel
import com.example.pamfinal.ui.viewmodel.pendapatan.DataAllPendapatanViewModel
import com.example.pamfinal.ui.viewmodel.pendapatan.DetailPendapatanViewModel
import com.example.pamfinal.ui.viewmodel.pendapatan.HomePendapatanViewModel
import com.example.pamfinal.ui.viewmodel.pendapatan.UpdatePendapatanViewModel
import com.example.pamfinal.ui.viewmodel.pengeluaran.DataAllPengeluaranViewModel
import com.example.pamfinal.ui.viewmodel.pengeluaran.DetailPengeluaranViewModel
import com.example.pamfinal.ui.viewmodel.pengeluaran.HomePengeluaranViewModel
import com.example.pamfinal.ui.viewmodel.pengeluaran.UpdatePengeluaranViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        //aset
        initializer {
            HomeAsetViewModel(aplikasiKeuangan().container.asetRepository)
        }
        initializer {
            InsertAsetViewModel(aplikasiKeuangan().container.asetRepository)
        }
        initializer {
            UpdateAsetViewModel(createSavedStateHandle(), asetRepository = aplikasiKeuangan().container.asetRepository)
        }

        //Kategori
        initializer {
            HomeKategoriViewModel(aplikasiKeuangan().container.kategoriRepository)
        }
        initializer {
            InsertKategoriViewModel(aplikasiKeuangan().container.kategoriRepository)
        }
        initializer {
            UpdateKategoriViewModel(createSavedStateHandle(), kategoriRepository = aplikasiKeuangan().container.kategoriRepository)
        }

        //Pendapatan
        initializer {
            HomePendapatanViewModel(aplikasiKeuangan().container.pendapatanRepository)
        }
        initializer {
            DataAllPendapatanViewModel(aplikasiKeuangan().container.pendapatanRepository)
        }
        initializer {
            DetailPendapatanViewModel(createSavedStateHandle(), aplikasiKeuangan().container.pendapatanRepository)
        }
        initializer {
            UpdatePendapatanViewModel(createSavedStateHandle(), pendapatanRepository = aplikasiKeuangan().container.pendapatanRepository)
        }

        //Pengeluaran
        initializer {
            HomePengeluaranViewModel(aplikasiKeuangan().container.pengeluaranRepository)
        }
        initializer {
            DataAllPengeluaranViewModel(aplikasiKeuangan().container.pengeluaranRepository)
        }
        initializer {
            DetailPengeluaranViewModel(createSavedStateHandle(), aplikasiKeuangan().container.pengeluaranRepository)
        }
        initializer {
            UpdatePengeluaranViewModel(createSavedStateHandle(), pengeluaranRepository = aplikasiKeuangan().container.pengeluaranRepository)
        }

        //Home Utama
        initializer {
            HomeUtamaViewModel(aplikasiKeuangan().container.pendapatanRepository)

        }
    }

}

fun CreationExtras.aplikasiKeuangan(): AsetApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AsetApplications)