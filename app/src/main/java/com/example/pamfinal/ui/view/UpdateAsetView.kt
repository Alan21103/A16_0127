package com.example.pamfinal.ui.view

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
import com.example.pamfinal.ui.viewmodel.UpdateAsetViewModel
import kotlinx.coroutines.launch

object DestinasiAsetUpdate : DestinasiNavigasi {
    override val route = "id_aset_update"
    override val titleRes = "Update Aset"
    const val id_aset = "id_aset"
    val routeWithArgs = "$route/{$id_aset}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAsetView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateAsetViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiAsetUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack

            )
        }
    ) { innerPadding ->
        EntryAsetBody(
            insertUiState = viewModel.uiState,
            onAsetValueChange = viewModel::updateAsetState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.editAset()
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