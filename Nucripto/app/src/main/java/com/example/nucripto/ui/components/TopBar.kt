package com.example.nucripto.ui.components

import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

fun TopAppBar(title: () -> Unit, colors: TopAppBarColors) {
    TODO("Not yet implemented")
}

private fun TopAppBarDefaults.smallTopAppBarColors(containerColor: Color): TopAppBarColors {
    TODO("Not yet implemented")
}
