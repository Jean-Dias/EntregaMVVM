package com.example.nucripto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nucripto.ui.components.CryptoButton
import com.seuprojeto.nucriipto.ui.components.TopBar

@Composable
fun SendScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = { TopBar("Enviar Cripto") },
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Aqui você implementa a lógica de envio")
            Spacer(modifier = Modifier.height(16.dp))
            CryptoButton("Voltar") { onBack() }
        }
    }
}
