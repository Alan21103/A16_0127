package com.example.pamfinal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pamfinal.ui.view.aset.DestinasiAsetEntry
import com.example.pamfinal.ui.view.aset.DestinasiAsetUpdate
import com.example.pamfinal.ui.view.aset.DestinasiHomeAset
import com.example.pamfinal.ui.view.DestinasiHomeUtama
import com.example.pamfinal.ui.view.aset.EntryAsetScreen
import com.example.pamfinal.ui.view.aset.HomeAsetScreen
import com.example.pamfinal.ui.view.HomeUtamaScreen
import com.example.pamfinal.ui.view.aset.UpdateAsetView
import com.example.pamfinal.ui.view.kategori.DestinasiHomeKategori
import com.example.pamfinal.ui.view.kategori.DestinasiKategoriEntry
import com.example.pamfinal.ui.view.kategori.DestinasiKategoriUpdate
import com.example.pamfinal.ui.view.kategori.EntryKategoriScreen
import com.example.pamfinal.ui.view.kategori.HomeKategoriScreen
import com.example.pamfinal.ui.view.kategori.UpdateKategoriView
import com.example.pamfinal.ui.view.pendapatan.DataAllPendapatanScreen
import com.example.pamfinal.ui.view.pendapatan.DestinasiDataAllPendapatan
import com.example.pamfinal.ui.view.pendapatan.DestinasiDetailPendapatan
import com.example.pamfinal.ui.view.pendapatan.DestinasiHomePendapatan
import com.example.pamfinal.ui.view.pendapatan.DestinasiUpdatePendapatan
import com.example.pamfinal.ui.view.pendapatan.DetailPendapatanScreen
import com.example.pamfinal.ui.view.pendapatan.HomePendapatanScreen
import com.example.pamfinal.ui.view.pendapatan.UpdatePendapatanView
import com.example.pamfinal.ui.view.pengeluaran.DataAllPengeluaranScreen
import com.example.pamfinal.ui.view.pengeluaran.DestinasiDataAllPengeluaran
import com.example.pamfinal.ui.view.pengeluaran.DestinasiDetailPengeluaran
import com.example.pamfinal.ui.view.pengeluaran.DestinasiHomePengeluaran
import com.example.pamfinal.ui.view.pengeluaran.DestinasiUpdatePengeluaran
import com.example.pamfinal.ui.view.pengeluaran.DetailPengeluaranScreen
import com.example.pamfinal.ui.view.pengeluaran.HomePengeluaranScreen
import com.example.pamfinal.ui.view.pengeluaran.UpdatePengeluaranView

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeUtama.route,
        modifier = Modifier
    ) {
        // Home Utama
        composable(DestinasiHomeUtama.route) {
            HomeUtamaScreen(navController = navController)
        }
        //Home Aset
        composable(DestinasiHomeAset.route) {
            HomeAsetScreen(
                navigateBack = {navController.navigate(DestinasiHomeUtama.route){
                    popUpTo(DestinasiHomeUtama.route){ inclusive = true}
                } },
                navigateToItemEntry = { navController.navigate(DestinasiAsetEntry.route) },
                navController = navController
            )
        }
        //Entry Aset
        composable(DestinasiAsetEntry.route) {
            EntryAsetScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeAset.route) {
                        popUpTo(DestinasiHomeAset.route) { inclusive = true }
                    }
                }
            )
        }

        //Update Aset
        composable(
            route = DestinasiAsetUpdate.routeWithArgs,
            arguments = listOf(navArgument(DestinasiAsetUpdate.id_aset){
                type = NavType.StringType
            })
        ) {
            UpdateAsetView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiAsetUpdate.route
                    ) {
                        popUpTo(DestinasiHomeAset.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(DestinasiHomeKategori.route) {
            HomeKategoriScreen(
                navigateBack = {navController.navigate(DestinasiHomeUtama.route){
                    popUpTo(DestinasiHomeUtama.route){ inclusive = true}
                } },
                navigateToItemEntry = { navController.navigate(DestinasiKategoriEntry.route) },
                navController = navController
            )
        }

        composable(DestinasiKategoriEntry.route) {
            EntryKategoriScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeKategori.route) {
                        popUpTo(DestinasiHomeKategori.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = DestinasiKategoriUpdate.routeWithArgs,
            arguments = listOf(navArgument(DestinasiKategoriUpdate.id_kategori){
                type = NavType.StringType
            })
        ) {
            UpdateKategoriView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiKategoriUpdate.route
                    ) {
                        popUpTo(DestinasiHomeKategori.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(DestinasiHomePendapatan.route) {
            HomePendapatanScreen(
                navigateBack = {navController.navigate(DestinasiHomeUtama.route){
                    popUpTo(DestinasiHomeUtama.route){ inclusive = true}
                } },
                navController = navController
            )
        }

        composable(DestinasiDataAllPendapatan.route) {
            DataAllPendapatanScreen(
                navController = navController,
                navigateBack = {navController.navigate(DestinasiHomePendapatan.route){
                    popUpTo(DestinasiHomePendapatan.route){ inclusive = true}
                } },
            )
        }

        composable(
            DestinasiDetailPendapatan.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPendapatan.id_pendapatan){
                    type = NavType.StringType
                }
            )
        ) {
            val id_pendapatan = it.arguments?.getString(DestinasiDetailPendapatan.id_pendapatan)
            id_pendapatan?.let {
                DetailPendapatanScreen(
                    navController = navController,
                    navigateBack = {
                        navController.navigate(DestinasiDataAllPendapatan.route) {
                            popUpTo(DestinasiDataAllPendapatan.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit = {
                        navController.navigate("${DestinasiUpdatePendapatan.route}/$id_pendapatan")
                    }
                )
            }
        }

        composable(
            route = DestinasiUpdatePendapatan.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdatePendapatan.id_pendapatan){
                type = NavType.StringType
            })
        ) {
            UpdatePendapatanView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiUpdatePendapatan.route
                    ) {
                        popUpTo(DestinasiDataAllPendapatan.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        //Home Pengeluaran
        composable(DestinasiHomePengeluaran.route) {
            HomePengeluaranScreen(
                navigateBack = {navController.navigate(DestinasiHomeUtama.route){
                    popUpTo(DestinasiHomeUtama.route){ inclusive = true}
                } },
                navController = navController
            )
        }

        //Data All Pengeluaran
        composable(DestinasiDataAllPengeluaran.route) {
            DataAllPengeluaranScreen(
                navController = navController,
                navigateBack = {navController.navigate(DestinasiHomePengeluaran.route){
                    popUpTo(DestinasiHomePengeluaran.route){ inclusive = true}
                } },
            )
        }

        //Detail Pengeluaran
        composable(
            DestinasiDetailPengeluaran.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPengeluaran.id_pengeluaran){
                    type = NavType.StringType
                }
            )
        ) {
            val id_pengeluaran = it.arguments?.getString(DestinasiDetailPengeluaran.id_pengeluaran)
            id_pengeluaran?.let {
                DetailPengeluaranScreen(
                    navController = navController,
                    navigateBack = {
                        navController.navigate(DestinasiDataAllPengeluaran.route) {
                            popUpTo(DestinasiDataAllPengeluaran.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit = {
                        navController.navigate("${DestinasiUpdatePengeluaran.route}/$id_pengeluaran")
                    }
                )
            }
        }

        composable(
            route = DestinasiUpdatePengeluaran.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdatePengeluaran.id_pengeluaran){
                type = NavType.StringType
            })
        ) {
            UpdatePengeluaranView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiUpdatePengeluaran.route
                    ) {
                        popUpTo(DestinasiDataAllPengeluaran.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}