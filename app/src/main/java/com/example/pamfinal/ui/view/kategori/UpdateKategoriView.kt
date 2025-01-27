package com.example.pamfinal.ui.view.kategori

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
import com.example.pamfinal.ui.viewmodel.kategori.UpdateKategoriViewModel
import kotlinx.coroutines.launch

object DestinasiKategoriUpdate : DestinasiNavigasi {
    override val route = "id_kategori_update"
    override val titleRes = "Update Kategori"
    const val id_kategori = "id_kategori"
    val routeWithArgs = "$route/{$id_kategori}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKategoriView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiKategoriUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack

            )
        }
    ) { innerPadding ->
        EntryKategoriBody(
            insertUiState = viewModel.uiState,
            onKategoriValueChange = viewModel::updateKategoriState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.editKategori()
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