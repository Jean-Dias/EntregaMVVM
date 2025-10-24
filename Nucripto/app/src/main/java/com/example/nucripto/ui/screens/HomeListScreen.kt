package com.example.nucripto.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.nucripto.data.model.Wallet
import com.example.nucripto.viewmodel.WalletViewModel
import com.example.nucripto.ui.components.CryptoButton
import com.seuprojeto.nucriipto.ui.components.TopBar
import com.example.nucripto.ui.components.WalletCard

@Composable
fun HomeScreen(viewModel: WalletViewModel, onNavigateToSend: () -> Unit) {
    val wallets by viewModel.wallets.collectAsState()

    Scaffold(
        topBar = { TopBar("NuCripto Home") },
        floatingActionButton = {
            CryptoButton("Enviar Cripto") { onNavigateToSend() }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(wallets) { wallet ->
                WalletCard(wallet)
            }
        }
    }
}

private fun LazyListScope.items(
    count: List<Wallet>,
    itemContent: Any
) {
        TODO("Not yet implemented")
}

fun Scaffold(topBar: Any, floatingActionButton: () -> Unit, content: Any) {
    TODO("Not yet implemented")
}

fun Scaffold(topBar: @Composable () -> Unit, floatingActionButton: Any, floatingActionButton: () -> Unit, content: Any) {}
