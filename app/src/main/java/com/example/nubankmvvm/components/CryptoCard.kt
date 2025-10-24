package com.example.nubankmvvmmvvm


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nubankmvvmmvvm.data.CryptoEntity

@Composable
fun CryptoCard(
    crypto: CryptoEntity,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = crypto.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = crypto.symbol)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "R$ ${crypto.price}")
                Text(
                    text = "${crypto.variation}%",
                    color = if (crypto.variation >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                    Button(onClick = onEdit) { Text("Editar") }
                    Button(onClick = onDelete, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)) { Text("Excluir") }
                }
            }
        }
    }
}