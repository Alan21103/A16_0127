package com.example.pamfinal.ui.view.pengeluaran

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pamfinal.navigation.DestinasiNavigasi
import com.example.pamfinal.ui.customwidget.CostumeTopAppBar
import com.example.pamfinal.ui.viewmodel.PenyediaViewModel
import com.example.pamfinal.ui.viewmodel.aset.HomeAsetViewModel
import com.example.pamfinal.ui.viewmodel.kategori.HomeKategoriViewModel
import com.example.pamfinal.ui.viewmodel.pengeluaran.UpdatePengeluaranViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePengeluaran : DestinasiNavigasi {
    override val route = "id_pengeluaran_update"
    override val titleRes = "Update Pengeluaran"
    const val id_pengeluaran = "id_pengeluaran"
    val routeWithArgs = "$route/{$id_pengeluaran}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePengeluaranView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePengeluaranViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelAst: HomeAsetViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelKtg: HomeKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePengeluaran.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack

            )
        }
    ) { innerPadding ->
        EntryPengeluaranBody(
            insertUiState = viewModel.uiState,
            onPengeluaranValueChange = viewModel::updatePengeluaranState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.editPengeluaran()
                    navigateBack()  // Pindahkan hanya jika update berhasil
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}