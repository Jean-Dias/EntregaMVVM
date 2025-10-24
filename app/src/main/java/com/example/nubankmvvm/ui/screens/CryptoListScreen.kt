package com.example.nubankmvvmmvvm


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nubankmvvmmvvm.components.CryptoCard
import com.example.nubankmvvmmvvm.components.CryptoForm
import com.example.nubankmvvmmvvm.data.CryptoEntity
import com.example.nubankmvvmmvvm.ui.CryptoViewModel
import com.example.nubankmvvmmvvm.ui.components.CryptoCard
import com.example.nubankmvvmmvvm.ui.components.CryptoForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoListScreen(navController: NavHostController, vm: CryptoViewModel) {
    val context = LocalContext.current
    val cryptos by vm.cryptos.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Criptos") }) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Filtros e ordenação (Row)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = vm.searchQuery,
                    onValueChange = { vm.searchQuery = it },
                    label = { Text("Buscar") },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = { vm.sortByVariationDesc = !vm.sortByVariationDesc }) {
                    Text(if (vm.sortByVariationDesc) "Maior var." else "Normal")
                }
            }

            // Lista (LazyColumn + Cards)
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(vm.filteredCryptos()) { crypto ->
                    CryptoCard(
                        crypto = crypto,
                        onClick = { navController.navigate("cryptoDetail/${crypto.id}") },
                        onEdit = { vm.loadCoinIntoForm(crypto) },
                        onDelete = {
                            vm.deleteCoin(
                                crypto,
                                onDone = { Toast.makeText(context, "Moeda removida", Toast.LENGTH_SHORT).show() },
                                onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                            )
                        }
                    )
                }
            }

            // Form de criar/editar moeda (Box/Column/TextFields/Buttons)
            CryptoForm(
                name = vm.formName,
                symbol = vm.formSymbol,
                price = vm.formPrice,
                variation = vm.formVariation,
                isEditing = vm.isEditingCoin,
                onNameChange = { vm.formName = it },
                onSymbolChange = { vm.formSymbol = it },
                onPriceChange = { vm.formPrice = it },
                onVariationChange = { vm.formVariation = it },
                onSave = {
                    vm.saveCoin(
                        onDone = { id -> Toast.makeText(context, "Salvo (id=$id)", Toast.LENGTH_SHORT).show() },
                        onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                    )
                },
                onCancel = { vm.resetCoinForm() }
            )
        }
    }
}