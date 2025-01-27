package com.example.pamfinal.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pamfinal.navigation.DestinasiNavigasi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pamfinal.ui.viewmodel.HomeUtamaViewModel
import com.example.pamfinal.ui.viewmodel.PenyediaViewModel

object DestinasiHomeUtama : DestinasiNavigasi {
    override val route = "home_utama"
    override val titleRes = "Home Utama"
}

@Composable
fun HomeUtamaScreen(
    navController: NavController,
    viewModel: HomeUtamaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val saldo by viewModel.saldo.collectAsState()
    val totalPendapatan by viewModel.totalPendapatan.collectAsState()
    val totalPengeluaran by viewModel.totalPengeluaran.collectAsState()
    val saldoColor by viewModel.saldoColor.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
    ) { paddingValues ->
        Box(
             modifier = Modifier
                 .fillMaxSize()
                 .clip(RoundedCornerShape(topEnd = 200.dp))
                 .background(Color(0xFF003153))
        ){}
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                ) {
                    Text(
                        text = "Selamat Datang ",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.White)
                    )
                    Text(
                        text = "Kael", // Bisa diganti dengan nama user dari ViewModel
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, color = Color.White)
                    )
                }
            }
            // Fungsi formatCurrency
            fun Double.formatCurrency(): String {
                return "Rp %,d".format(this.toLong()).replace(',', '.')
            }

            // Saldo Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp, horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountBalanceWallet,
                        contentDescription = "Saldo Icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Saldo Anda",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = (saldo as Double).formatCurrency(), // Pemanggilan formatCurrency
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = saldoColor
                        )
                    }
                }
            }

            // Ringkasan Keuangan Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ringkasan Keuangan",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDownward,
                                contentDescription = "Pemasukan",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Text(
                                text = "Pemasukan",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = (totalPendapatan as Double).formatCurrency(), // Pemanggilan formatCurrency
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "Pengeluaran",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(32.dp)
                            )
                            Text(
                                text = "Pengeluaran",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = (totalPengeluaran as Double).formatCurrency(), // Pemanggilan formatCurrency
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun BottomNavigationBar(
    navController: NavController) {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.shadow(8.dp) // Tambahkan shadow pada navigation bar
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home Utama") },
            label = { Text("Home") },
            selected = selectedItem == 0,
            onClick = {
                selectedItem = 0
                navController.navigate("home_utama")
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.DateRange, contentDescription = "Home Aset") },
            label = { Text("Aset") },
            selected = selectedItem == 1,
            onClick = {
                selectedItem = 1
                navController.navigate("home_aset")
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Email, contentDescription = "Home Pengeluaran") },
            label = { Text("Keluar") },
            selected = selectedItem == 2,
            onClick = {
                selectedItem = 2
                navController.navigate("home_pengeluaran")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Email, contentDescription = "Home Pemasukan") },
            label = { Text("Masuk") },
            selected = selectedItem == 3,
            onClick = {
                selectedItem = 3
                navController.navigate("home_pendapatan")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.DateRange, contentDescription = "Home Kategori") },
            label = { Text("Kategori") },
            selected = selectedItem == 4,
            onClick = {
                selectedItem = 4
                navController.navigate("home_kategori")
            }
        )
    }
}




