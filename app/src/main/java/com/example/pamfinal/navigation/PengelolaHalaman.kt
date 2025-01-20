package com.example.pamfinal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pamfinal.ui.view.DestinasiAsetEntry
import com.example.pamfinal.ui.view.DestinasiHomeAset
import com.example.pamfinal.ui.view.HomeAsetScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeAset.route,
        modifier = Modifier
    ) {
        composable(DestinasiHomeAset.route) {
            HomeAsetScreen(
                navigateToItemEntry = { navController.navigate(DestinasiAsetEntry.route) }
            )
        }
    }
}