package com.example.pamfinal.ui.view.pengeluaran

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pamfinal.navigation.DestinasiNavigasi
import com.example.pamfinal.ui.customwidget.CostumeTopAppBar
import com.example.pamfinal.ui.customwidget.DynamicSelectedTextField
import com.example.pamfinal.ui.viewmodel.HomeUtamaViewModel
import com.example.pamfinal.ui.viewmodel.PenyediaViewModel
import com.example.pamfinal.ui.viewmodel.aset.HomeAsetUiState
import com.example.pamfinal.ui.viewmodel.aset.HomeAsetViewModel
import com.example.pamfinal.ui.viewmodel.kategori.HomeKategoriUiState
import com.example.pamfinal.ui.viewmodel.kategori.HomeKategoriViewModel
import com.example.pamfinal.ui.viewmodel.pengeluaran.HomePengeluaranViewModel
import com.example.pamfinal.ui.viewmodel.pengeluaran.InsertPengeluaranUiEvent
import com.example.pamfinal.ui.viewmodel.pengeluaran.InsertPengeluaranUiState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DestinasiHomePengeluaran : DestinasiNavigasi {
    override val route = "home_pengeluaran"
    override val titleRes = "Home Pengeluaran"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePengeluaranScreen(
    navController: NavController,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomePengeluaranViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelAst: HomeAsetViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelSld: HomeUtamaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelKtg: HomeKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showDialog by remember { mutableStateOf(false) }

    // Fetch data from HomeUtamaViewModel
    LaunchedEffect(Unit) {
        viewModelSld.fetchData()
    }

    val saldo = viewModelSld.saldo.collectAsState().value
    val saldoColor = viewModelSld.saldoColor.collectAsState().value

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePengeluaran.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryPengeluaranBody(
            insertUiState = viewModel.uiState,
            onPengeluaranValueChange = viewModel::updateInsertPglState,
            onSaveClick = {
                coroutineScope.launch {
                    if (saldo < 0.0) {
                        showDialog = true
                    } else {
                        viewModel.insertPengeluaran()
                        navController.navigate("data_pengeluaran") {
                            popUpTo("home_pengeluaran") { inclusive = true }
                        }
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi") },
            text = { Text("Keuangan anda sudah minus, apakah anda yakin menambah data pengeluaran?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    coroutineScope.launch {
                        viewModel.insertPengeluaran()
                        navController.navigate("data_pengeluaran") {
                            popUpTo("home_pengeluaran") { inclusive = true }
                        }
                    }
                }) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}


@Composable
fun EntryPengeluaranBody(
    insertUiState: InsertPengeluaranUiState,
    onPengeluaranValueChange: (InsertPengeluaranUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertPengeluaranUiEvent,
            onValueChange = onPengeluaranValueChange,
            modifier = Modifier.fillMaxWidth(),
            viewModelAst = viewModel(),
            viewModelKtg = viewModel()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertPengeluaranUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPengeluaranUiEvent) -> Unit = {},
    enabled: Boolean = true,
    viewModelAst: HomeAsetViewModel,
    viewModelKtg: HomeKategoriViewModel
) {
    val asetUiState = viewModelAst.astUIState
    val kategoriUiState = viewModelKtg.ktgUIState

    when (asetUiState) {
        is HomeAsetUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        is HomeAsetUiState.Error -> Text("Gagal memuat data aset", color = MaterialTheme.colorScheme.error)
        is HomeAsetUiState.Success -> when (kategoriUiState) {
            is HomeKategoriUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            is HomeKategoriUiState.Error -> Text("Gagal memuat data kategori", color = MaterialTheme.colorScheme.error)
            is HomeKategoriUiState.Success -> {
                val asetList = asetUiState.aset.map { it.nama_aset }
                val kategoriList = kategoriUiState.kategori.map { it.nama_kategori }

                var showDatePicker by remember { mutableStateOf(false) }
                var selectedDate by remember { mutableStateOf(insertUiEvent.tanggal_transaksi) }

                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DynamicSelectedTextField(
                        selectedValue = asetUiState.aset.find { it.id_aset == insertUiEvent.id_aset }?.nama_aset ?: "Pilih Aset",
                        options = asetList, // Menampilkan list nama aset
                        label = "Pilih Aset",
                        onValueChangedEvent = { selectedAset ->
                            val selectedId = asetUiState.aset.first { it.nama_aset == selectedAset }.id_aset
                            onValueChange(insertUiEvent.copy(id_aset = selectedId))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )


                    DynamicSelectedTextField(
                        selectedValue = kategoriUiState.kategori.find { it.id_kategori == insertUiEvent.id_kategori }?.nama_kategori ?: "Pilih Kategori",
                        options = kategoriList, // Menampilkan list nama kategori
                        label = "Pilih Kategori",
                        onValueChangedEvent = { selectedKategori ->
                            val selectedId = kategoriUiState.kategori.first { it.nama_kategori == selectedKategori }.id_kategori
                            onValueChange(insertUiEvent.copy(id_kategori = selectedId))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = if (selectedDate.isEmpty()) "Pilih Tanggal" else selectedDate,
                        onValueChange = {},
                        label = { Text("Tanggal Transaksi", color = Color.Black) },
                        placeholder = { Text("Pilih Tanggal", color = Color.Gray) },  // Placeholder abu-abu sebelum dipilih
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDatePicker = true },
                        enabled = false,  // Non-aktifkan manual input untuk hanya memilih dengan date picker
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = if (selectedDate.isEmpty()) Color.Gray else Color.Black,  // Abu-abu sebelum dipilih, hitam setelahnya
                            focusedTextColor = Color.Black,    // Warna teks saat fokus
                            cursorColor = Color.Black,         // Warna kursor
                            disabledTextColor = if (selectedDate.isEmpty()) Color.Gray else Color.Black,   // Warna teks saat nonaktif
                            disabledLabelColor = Color.Gray,  // Warna label saat nonaktif
                            disabledPlaceholderColor = Color.Gray, // Placeholder tetap abu-abu sebelum dipilih
                            unfocusedBorderColor = Color.Black,
                            focusedBorderColor = Color.Black
                        )
                    )
                    if (showDatePicker) {
                        val datePickerState = rememberDatePickerState()

                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    val selectedMillis = datePickerState.selectedDateMillis
                                    selectedMillis?.let {
                                        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                                            Date(it)
                                        )
                                        selectedDate = formattedDate
                                        onValueChange(insertUiEvent.copy(tanggal_transaksi = formattedDate))
                                    }
                                    showDatePicker = false
                                }) {
                                    Text("Pilih")
                                }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }

                    OutlinedTextField(
                        value = insertUiEvent.total,
                        onValueChange = { onValueChange(insertUiEvent.copy(total = it)) },
                        label = { Text("Total") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = enabled,
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = insertUiEvent.catatan,
                        onValueChange = { onValueChange(insertUiEvent.copy(catatan = it)) },
                        label = { Text("Catatan") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = enabled,
                        singleLine = true
                    )
                }
            }
        }
    }
}



@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home Utama") },
            label = { Text("Home") },
            selected = selectedItem == 0,
            onClick = {
                selectedItem = 0
                navController.navigate("home_pengeluaran")
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.DateRange, contentDescription = "Data All") },
            label = { Text("Data Pengeluaran") },
            selected = selectedItem == 1,
            onClick = {
                selectedItem = 1
                navController.navigate("data_pengeluaran")
            }
        )
    }
}