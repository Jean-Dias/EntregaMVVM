@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nubankmvvmmvvm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nubankmvvmmvvm.data.AppDatabase
import com.example.nubankmvvmmvvm.data.Investimento
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf("cdb") }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF7B1FA2))
            ) {
                when (currentScreen) {
                    "cdb" -> CdbScreen(
                        onNavigateToPoupanca = { currentScreen = "poupanca" }
                    )
                    "poupanca" -> PoupancaScreen(
                        onNavigateToInvestimentos = { currentScreen = "investimentos" },
                        onNavigateToCdb = { currentScreen = "cdb" }
                    )
                    "investimentos" -> PoupancaInvestimentoScreen(
                        onBack = { currentScreen = "poupanca" }
                    )
                }
            }
        }
    }
}

// --- Tela de Investimentos em CDB ---
@Composable
fun CdbScreen(onNavigateToPoupanca: () -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Investir em CDB",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Encontre opções com a maior rentabilidade e segurança.",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        InvestimentoCard(
            title = "CDB DI",
            subtitle = "Rende mais que a poupança!",
            details = "100% do CDI • Liquidez diária",
            buttonText = "Investir agora"
        )
        Spacer(modifier = Modifier.height(16.dp))
        InvestimentoCard(
            title = "CDB Prefixado",
            subtitle = "Rendimento fixo até o vencimento.",
            details = "12% ao ano • Vencimento em 2026",
            buttonText = "Ver detalhes"
        )
        Spacer(modifier = Modifier.height(16.dp))
        InvestimentoCard(
            title = "CDB IPCA+",
            subtitle = "Rende de acordo com a inflação.",
            details = "IPCA + 5,5% ao ano",
            buttonText = "Ver detalhes"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNavigateToPoupanca,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A05BE)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Ir para Poupança", color = Color.White)
        }
    }
}

// --- Tela de Poupança ---
@Composable
fun PoupancaScreen(onNavigateToInvestimentos: () -> Unit, onNavigateToCdb: () -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Sua Poupança",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Simples de usar, rende 70% da Selic.",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        InvestimentoCard(
            title = "Poupança Nu",
            subtitle = "Seu dinheiro seguro e rendendo.",
            details = "Rendimento mensal • Protegido pelo FGC",
            buttonText = "Depositar"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNavigateToInvestimentos,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A05BE)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Ir para tela de Investimentos", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onNavigateToCdb,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Voltar para CDB", color = Color.White)
        }
    }
}

// --- Tela de CRUD de Investimentos (com Room e validação) ---
@Composable
fun PoupancaInvestimentoScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val investimentoDao = db.investimentoDao()
    val scope = rememberCoroutineScope()

    var valor by remember { mutableStateOf("") }
    var investimentos by remember { mutableStateOf(listOf<Investimento>()) }

    // Carrega os investimentos do banco
    LaunchedEffect(Unit) {
        investimentos = investimentoDao.getAll()
    }

    val total = investimentos.sumOf { it.valor }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Investimentos", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF8A05BE))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF7B1FA2))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Gerencie seus Investimentos",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = valor,
                onValueChange = { valor = it },
                label = { Text("Digite o valor a investir", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        val valorDouble = valor.toDoubleOrNull()
                        if (valorDouble != null && valorDouble > 0) {
                            scope.launch {
                                val investimento = Investimento(nome = "Poupança", valor = valorDouble)
                                investimentoDao.insert(investimento)
                                investimentos = investimentoDao.getAll()
                                valor = ""
                            }
                        } else {
                            Toast.makeText(context, "Digite um valor válido!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A05BE)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Investir", color = Color.White)
                }

                Button(
                    onClick = {
                        if (investimentos.isNotEmpty()) {
                            scope.launch {
                                investimentoDao.delete(investimentos.last())
                                investimentos = investimentoDao.getAll()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Deletar", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Saldo total investido: R$ %.2f".format(total),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Voltar", color = Color.White)
            }
        }
    }
}

// --- Componentes Reutilizáveis ---
@Composable
fun InvestimentoCard(title: String, subtitle: String, details: String, buttonText: String) {
    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(details, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A05BE)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(buttonText, color = Color.White, fontSize = 16.sp)
            }
        }
    }
}
