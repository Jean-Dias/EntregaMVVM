@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nubankmvvmmvvm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nubankmvvmmvvm.data.AppDatabase
import com.example.nubankmvvmmvvm.data.Investimento
import kotlinx.coroutines.launch

/**
 * DepositScreen.kt
 *
 * Composable de gerenciamento de investimentos + ViewModel + Factory.
 * - Usa Material3 (opt-in ExperimentalMaterial3Api)
 * - Usa viewModel() do lifecycle-compose com Factory
 * - Exibe lista reativa usando mutableStateListOf + derivedStateOf
 *
 * Substitua o pacote/imports conforme a estrutura do seu projeto se necessário.
 */

// -------------------- Composable DepositScreen --------------------
@Composable
fun DepositScreen(
    onBack: () -> Unit,
    viewModel: InvestimentoViewModel = viewModel(
        factory = InvestimentoViewModelFactory(AppDatabase.getDatabase(LocalContext.current))
    )
) {
    // 'investimentos' exposto como State<List<Investimento>> no ViewModel — delegação com 'by' funciona aqui
    val investimentos by viewModel.investimentos

    var nome by remember { mutableStateOf(TextFieldValue("")) }
    var valor by remember { mutableStateOf(TextFieldValue("")) }
    val isAddEnabled = nome.text.isNotBlank() && valor.text.toDoubleOrNull() != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gerenciar Investimentos") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Investimento") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = valor,
                onValueChange = { valor = it },
                label = { Text("Valor Investido (ex: 1000.0)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val valorDouble = valor.text.toDoubleOrNull()
                    if (nome.text.isNotBlank() && valorDouble != null) {
                        viewModel.inserirInvestimento(nome.text, valorDouble)
                        nome = TextFieldValue("")
                        valor = TextFieldValue("")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isAddEnabled
            ) {
                Text("Adicionar Investimento")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Investimentos cadastrados:", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de investimentos
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(investimentos) { investimento ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(investimento.nome, style = MaterialTheme.typography.titleMedium)
                                Text("R$ %.2f".format(investimento.valor))
                            }
                            IconButton(onClick = { viewModel.excluirInvestimento(investimento) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Excluir")
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------- ViewModel --------------------
class InvestimentoViewModel(private val db: AppDatabase) : ViewModel() {

    private val dao = db.investimentoDao()

    // Lista mutável de estados — atualize usando as funções do ViewModel
    private val _investimentos = mutableStateListOf<Investimento>()

    // Exponha como State<List<Investimento>> para uso direto com 'by' em Composables
    val investimentos: State<List<Investimento>> = derivedStateOf { _investimentos.toList() }

    init {
        carregarInvestimentos()
    }

    private fun carregarInvestimentos() {
        viewModelScope.launch {
            // carrega do DAO (supondo que dao.getAll() seja suspend e retorne List<Investimento>)
            val dados = dao.getAll()
            _investimentos.clear()
            _investimentos.addAll(dados)
        }
    }

    fun inserirInvestimento(nome: String, valor: Double) {
        viewModelScope.launch {
            dao.insert(Investimento(nome = nome, valor = valor))
            // recarrega lista após inserção
            val dados = dao.getAll()
            _investimentos.clear()
            _investimentos.addAll(dados)
        }
    }

    fun excluirInvestimento(investimento: Investimento) {
        viewModelScope.launch {
            dao.delete(investimento)
            val dados = dao.getAll()
            _investimentos.clear()
            _investimentos.addAll(dados)
        }
    }
}

// -------------------- ViewModel Factory --------------------
class InvestimentoViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InvestimentoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InvestimentoViewModel(db) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}
