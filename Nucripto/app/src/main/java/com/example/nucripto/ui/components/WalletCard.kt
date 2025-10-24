package com.example.nucripto.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nucripto.data.model.Wallet


@Composable
fun WalletCard(wallet: Wallet, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = wallet.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "${wallet.balance} BTC", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

fun Column(modifier: Modifier, content: () -> Unit) {
            TODO("Not yet implemented")
}

fun Card(
    modifier: Modifier,
    shape: RoundedCornerShape,
    elevation: CardElevation,
    content: () -> Unit
) {
    TODO("Not yet implemented")
}
