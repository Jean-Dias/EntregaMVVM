package com.example.nucripto.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nucripto.viewmodel.WalletViewModel
import com.example.nucripto.ui.screens.HomeScreen
import com.example.nucripto.ui.screens.SendScreen

@Composable
fun NavGraph(viewModel: WalletViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(viewModel) { navController.navigate("send") }
        }
        composable("send") {
            SendScreen { navController.popBackStack() }
        }
    }
}

private fun NavGraphBuilder.composable(route: String, content: () -> Unit) {}


