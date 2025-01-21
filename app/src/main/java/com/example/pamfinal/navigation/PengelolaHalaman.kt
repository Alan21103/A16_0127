package com.example.pamfinal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pamfinal.ui.view.DestinasiAsetEntry
import com.example.pamfinal.ui.view.DestinasiAsetUpdate
import com.example.pamfinal.ui.view.DestinasiHomeAset
import com.example.pamfinal.ui.view.EntryAsetScreen
import com.example.pamfinal.ui.view.HomeAsetScreen
import com.example.pamfinal.ui.view.UpdateAsetView

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeAset.route,
        modifier = Modifier
    ) {
        composable(DestinasiHomeAset.route) {
            HomeAsetScreen(
                navigateToItemEntry = { navController.navigate(DestinasiAsetEntry.route) },
                navController = navController
            )
        }

        composable(DestinasiAsetEntry.route) {
            EntryAsetScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeAset.route) {
                        popUpTo(DestinasiHomeAset.route) { inclusive = true }
                    }
                }
            )
        }

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
    }
}