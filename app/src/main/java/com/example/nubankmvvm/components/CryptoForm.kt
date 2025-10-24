package com.example.nubankmvvmmvvm



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CryptoForm(
    name: String,
    symbol: String,
    price: String,
    variation: String,
    isEditing: Boolean,
    onNameChange: (String) -> Unit,
    onSymbolChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onVariationChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = if (isEditing) "Editar moeda" else "Adicionar moeda", style = MaterialTheme.typography.titleSmall)
            TextField(value = name, onValueChange = onNameChange, label = { Text("Nome") })
            TextField(value = symbol, onValueChange = onSymbolChange, label = { Text("Símbolo") })
            TextField(value = price, onValueChange = onPriceChange, label = { Text("Preço") })
            TextField(value = variation, onValueChange = onVariationChange, label = { Text("Variação (%)") })
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onSave) { Text(if (isEditing) "Salvar" else "Adicionar") }
                if (isEditing) Button(onClick = onCancel) { Text("Cancelar") }
            }
        }
    }
}