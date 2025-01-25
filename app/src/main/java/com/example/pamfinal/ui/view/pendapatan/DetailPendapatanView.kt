package com.example.pamfinal.ui.view.pendapatan

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pamfinal.model.Pendapatan
import com.example.pamfinal.navigation.DestinasiNavigasi
import com.example.pamfinal.ui.customwidget.CostumeTopAppBar
import com.example.pamfinal.ui.viewmodel.PenyediaViewModel
import com.example.pamfinal.ui.viewmodel.pendapatan.DetailPendapatanUiState
import com.example.pamfinal.ui.viewmodel.pendapatan.DetailPendapatanViewModel
import com.example.pamfinal.ui.viewmodel.pendapatan.toPdt

object DestinasiDetailPendapatan : DestinasiNavigasi {
    override val route = "id_pendapatan_detail"
    override val titleRes = "Detail Pendapatan"
    const val id_pendapatan = "id_pendapatan"
    val routeWithArgs = "$route/{$id_pendapatan}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPendapatanScreen(
    navigateToEdit: () -> Unit,
    navController: NavController,
    navigateBack: () -> Unit,
    viewModel: DetailPendapatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val context = LocalContext.current
    val uiState = viewModel.detailUiState

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPendapatan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {viewModel.getPendapatanById()}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEdit,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pendapatan"
                )
            }
        }
    ) { innerPadding ->
        DetailBodyPdt(
            uiState = uiState,
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = {
                viewModel.deletePendapatan() // Memanggil fungsi hapus di ViewModel
                Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                navController.navigateUp() // Navigasi kembali setelah menghapus
            }
        )
    }
}



@Composable
fun DetailBodyPdt(
    uiState: DetailPendapatanUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    when {
        uiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.isError -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        uiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Menampilkan detail pendapatan
                ItemDetailPdt(
                    pendapatan = uiState.detailUiEvent.toPdt(),
                    modifier = Modifier.fillMaxWidth()
                )
                // Tombol Delete
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(text = "Delete", color = Color.White)
                }
            }
        }
    }

    // Konfirmasi dialog hapus
    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick()
            },
            onDeleteCancel = { deleteConfirmationRequired = false }
        )
    }
}


@Composable
fun ItemDetailPdt(
    modifier: Modifier = Modifier,
    pendapatan: Pendapatan
){
    Card(
        modifier = modifier.fillMaxWidth().padding(top = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            ComponentDetailPdt(judul = "ID Pendapatan", isinya = pendapatan.id_pendapatan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPdt(judul = "ID Aset", isinya = pendapatan.id_aset)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPdt(judul = "ID Kategori", isinya = pendapatan.id_kategori)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPdt(judul = "Tanggal Transaksi", isinya = pendapatan.tanggal_transaksi)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPdt(judul = "Total", isinya = pendapatan.total)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPdt(judul = "Catatan", isinya = pendapatan.catatan)
        }
    }
}

@Composable
fun ComponentDetailPdt(
    modifier: Modifier = Modifier,
    judul:String,
    isinya:String
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}