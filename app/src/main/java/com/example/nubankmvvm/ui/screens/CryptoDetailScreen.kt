package com.example.nubankmvvmmvvm


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nubankmvvmmvvm.data.CryptoEntity
import com.example.nubankmvvmmvvm.data.TransactionEntity
import com.example.nubankmvvmmvvm.ui.CryptoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoDetailScreen(navController: NavHostController, vm: CryptoViewModel, crypto: CryptoEntity) {
    val context = LocalContext.current
    val txsFlow = remember { vm.transactionsFor(crypto.id) }
    val transactions by txsFlow.collectAsState(initial = emptyList())

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("${crypto.name} (${crypto.symbol})") },
            navigationIcon = { TextButton(onClick = { navController.popBackStack() }) { Text("Voltar") } }
        )
    }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Preço Atual: R$ ${crypto.price}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Variação: ${crypto.variation}%")

            // Form transação
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { vm.txType = if (vm.txType == "BUY") "SELL" else "BUY" }) { Text(vm.txType) }
                TextField(value = vm.txAmount, onValueChange = { vm.txAmount = it }, label = { Text("Qtd") }, modifier = Modifier.weight(1f))
                TextField(value = vm.txPrice, onValueChange = { vm.txPrice = it }, label = { Text("Preço") }, modifier = Modifier.weight(1f))
                Button(onClick = {
                    vm.addTransaction(
                        coinId = crypto.id,
                        onDone = { Toast.makeText(context, "Transação adicionada", Toast.LENGTH_SHORT).show() },
                        onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                    )
                }) { Text("Add") }
            }

            // Lista de transações
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                items(transactions) { tx ->
                    TransactionCard(
                        tx = tx,
                        onDelete = {
                            vm.deleteTransaction(
                                tx,
                                onDone = { Toast.makeText(context, "Transação excluída", Toast.LENGTH_SHORT).show() },
                                onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                            )
                        },
                        onEdit = {
                            vm.bumpTransactionPrice(
                                tx,
                                onDone = { Toast.makeText(context, "Preço +1.0", Toast.LENGTH_SHORT).show() },
                                onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionCard(
    tx: TransactionEntity,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card {
        Column(Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("${tx.type} — Qtd: ${tx.amount} @ R$ ${tx.price}")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onEdit) { Text("Editar") }
                Button(onClick = onDelete, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)) { Text("Excluir") }
            }
        }
    }
}