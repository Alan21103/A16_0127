package com.example.pamfinal.ui.view.pengeluaran

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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.pamfinal.model.Pengeluaran
import com.example.pamfinal.navigation.DestinasiNavigasi
import com.example.pamfinal.ui.customwidget.CostumeTopAppBar
import com.example.pamfinal.ui.viewmodel.PenyediaViewModel
import com.example.pamfinal.ui.viewmodel.pengeluaran.DataAllPengeluaranUiState
import com.example.pamfinal.ui.viewmodel.pengeluaran.DataAllPengeluaranViewModel

object DestinasiDataAllPengeluaran : DestinasiNavigasi {
    override val route = "data_pengeluaran"
    override val titleRes = "Data Pengeluaran"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataAllPengeluaranScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DataAllPengeluaranViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getPgl()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDataAllPengeluaran.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getPgl() }
            )
        },
    ) { innerPadding ->
        DataAllPengeluaranStatus(
            homeUiState = viewModel.pglUIState,
            retryAction = { viewModel.getPgl() },
            modifier = Modifier.padding(innerPadding).padding(top = 0.dp),
            navController = navController
        )
    }
}

@Composable
fun DataAllPengeluaranStatus(
    homeUiState: DataAllPengeluaranUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    when (homeUiState) {
        is DataAllPengeluaranUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DataAllPengeluaranUiState.Success -> {
            if (homeUiState.pengeluaran.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Pengeluaran")
                }
            } else {
                DataAllPengeluaranLayout(
                    pengeluaran = homeUiState.pengeluaran,
                    modifier = modifier.fillMaxWidth(),
                    navController = navController
                )
            }
        }
        is DataAllPengeluaranUiState.Error -> {
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
fun DataAllPengeluaranLayout(
    pengeluaran: List<Pengeluaran>,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pengeluaran) { item ->
            PengeluaranCard(
                pengeluaran = item,
                modifier = Modifier.fillMaxWidth(),
                onDetailClick = { navController.navigate("${DestinasiDetailPengeluaran.route}/${item.id_pengeluaran}") }
            )
        }
    }
}

@Composable
fun PengeluaranCard(
    pengeluaran: Pengeluaran,
    modifier: Modifier = Modifier,
    onDetailClick: (Pengeluaran) -> Unit, // Callback untuk detail
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF44336)) // Warna hijau
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Pengeluaran",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Divider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp)

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoRow(label = "Total:", value = formatCurrency(pengeluaran.total))
                InfoRow(label = "ID Pengeluaran:", value = pengeluaran.id_pengeluaran)
                InfoRow(label = "Tanggal:", value = pengeluaran.tanggal_transaksi)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {onDetailClick(pengeluaran) }) { // Kirim ID
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