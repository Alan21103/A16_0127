package com.example.pamfinal.ui.view.pendapatan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pamfinal.R
import com.example.pamfinal.model.Pendapatan
import com.example.pamfinal.navigation.DestinasiNavigasi
import com.example.pamfinal.ui.customwidget.CostumeTopAppBar
import com.example.pamfinal.ui.viewmodel.PenyediaViewModel
import com.example.pamfinal.ui.viewmodel.pendapatan.DataAllPendapatanUiState
import com.example.pamfinal.ui.viewmodel.pendapatan.DataAllPendapatanViewModel

object DestinasiDataAllPendapatan : DestinasiNavigasi {
    override val route = "data_pendapatan"
    override val titleRes = "Data Pendapatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataAllPendapatanScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DataAllPendapatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDataAllPendapatan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getPdt() }
            )
        },
    ) { innerPadding ->
        DataAllPendapatanStatus(
            homeUiState = viewModel.pdtUIState,
            retryAction = { viewModel.getPdt() },
            modifier = Modifier.padding(innerPadding).padding(top = 0.dp),
            navController = navController
        )
    }
}

@Composable
fun DataAllPendapatanStatus(
    homeUiState: DataAllPendapatanUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    when (homeUiState) {
        is DataAllPendapatanUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DataAllPendapatanUiState.Success -> {
            if (homeUiState.pendapatan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Pendapatan")
                }
            } else {
                DataAllPendapatanLayout(
                    pendapatan = homeUiState.pendapatan,
                    modifier = modifier.fillMaxWidth(),
                    navController = navController
                )
            }
        }
        is DataAllPendapatanUiState.Error -> {
            OnError(retryAction, modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(30.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun DataAllPendapatanLayout(
    pendapatan: List<Pendapatan>,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pendapatan) { item ->
            PendapatanCard(
                pendapatan = item,
                modifier = Modifier.fillMaxWidth(),
                onDetailClick = { navController.navigate("${DestinasiDetailPendapatan.route}/${item.id_pendapatan}") }
            )
        }
    }
}

@Composable
fun PendapatanCard(
    pendapatan: Pendapatan,
    modifier: Modifier = Modifier,
    onDetailClick: (Pendapatan) -> Unit, // Callback untuk detail
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)) // Warna hijau
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Pendapatan",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Divider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp)

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoRow(label = "Total:", value = formatCurrency(pendapatan.total))
                InfoRow(label = "ID Pendapatan:", value = pendapatan.id_pendapatan)
                InfoRow(label = "Tanggal:", value = pendapatan.tanggal_transaksi)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {onDetailClick(pendapatan) }) { // Kirim ID
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.8f))
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
        )
    }
}

fun formatCurrency(amount: String): String {
    return "Rp $amount"
}