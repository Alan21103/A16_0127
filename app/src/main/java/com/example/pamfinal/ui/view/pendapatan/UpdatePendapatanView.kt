package com.example.pamfinal.ui.view.pendapatan

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
import com.example.pamfinal.ui.viewmodel.pendapatan.UpdatePendapatanViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePendapatan : DestinasiNavigasi {
    override val route = "id_pendapatan_update"
    override val titleRes = "Update Pendapatan"
    const val id_pendapatan = "id_pendapatan"
    val routeWithArgs = "$route/{$id_pendapatan}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePendapatanView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePendapatanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelAst: HomeAsetViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelKtg: HomeKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePendapatan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack

            )
        }
    ) { innerPadding ->
        EntryPendapatanBody(
            insertUiState = viewModel.uiState,
            onPendapatanValueChange = viewModel::updatePendapatanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.editPendapatan()
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