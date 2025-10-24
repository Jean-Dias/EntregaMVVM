package com.example.nubankmvvmmvvm


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nubankmvvmmvvm.data.CryptoEntity
import com.example.nubankmvvmmvvm.data.CryptoRepository
import com.example.nubankmvvmmvvm.data.TransactionEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CryptoViewModel(private val repo: CryptoRepository) : ViewModel() {

    // Lista reativa de moedas
    val cryptos: StateFlow<List<CryptoEntity>> = repo.getAllCryptos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Estados do formulário de moedas (remember/mutableStateOf)
    var formName by mutableStateOf("")
    var formSymbol by mutableStateOf("")
    var formPrice by mutableStateOf("")
    var formVariation by mutableStateOf("")
    var isEditingCoin by mutableStateOf(false)
    var editingCoinId by mutableStateOf<Int?>(null)

    // Filtro e ordenação
    var searchQuery by mutableStateOf("")
    var sortByVariationDesc by mutableStateOf(false)

    fun filteredCryptos(): List<CryptoEntity> {
        val base = cryptos.value.filter {
            searchQuery.isBlank() ||
                    it.name.contains(searchQuery, true) ||
                    it.symbol.contains(searchQuery, true)
        }
        return if (sortByVariationDesc) base.sortedByDescending { it.variation } else base
    }

    fun loadCoinIntoForm(c: CryptoEntity) {
        editingCoinId = c.id
        formName = c.name
        formSymbol = c.symbol
        formPrice = c.price.toString()
        formVariation = c.variation.toString()
        isEditingCoin = true
    }

    fun resetCoinForm() {
        formName = ""; formSymbol = ""; formPrice = ""; formVariation = ""
        isEditingCoin = false; editingCoinId = null
    }

    fun saveCoin(onDone: (Int) -> Unit, onError: (String) -> Unit) = viewModelScope.launch {
        val price = formPrice.toDoubleOrNull()
        val variation = formVariation.toDoubleOrNull()
        if (formName.isBlank() || formSymbol.isBlank() || price == null || variation == null) {
            onError("Dados inválidos")
            return@launch
        }
        try {
            if (isEditingCoin && editingCoinId != null) {
                repo.updateCrypto(
                    CryptoEntity(
                        id = editingCoinId!!,
                        name = formName,
                        symbol = formSymbol,
                        price = price,
                        variation = variation
                    )
                )
                onDone(editingCoinId!!)
            } else {
                val id = repo.insertCrypto(
                    CryptoEntity(name = formName, symbol = formSymbol, price = price, variation = variation)
                ).toInt()
                onDone(id)
            }
            resetCoinForm()
        } catch (e: Exception) {
            onError("Erro ao salvar: ${e.message}")
        }
    }

    fun deleteCoin(c: CryptoEntity, onDone: () -> Unit, onError: (String) -> Unit) = viewModelScope.launch {
        try { repo.deleteCrypto(c); onDone() } catch (e: Exception) { onError("Erro ao excluir: ${e.message}") }
    }

    // Detalhe / transações
    fun transactionsFor(coinId: Int): Flow<List<TransactionEntity>> = repo.getTransactionsFor(coinId)

    var txType by mutableStateOf("BUY")
    var txAmount by mutableStateOf("")
    var txPrice by mutableStateOf("")

    fun resetTxForm() { txType = "BUY"; txAmount = ""; txPrice = "" }

    fun addTransaction(coinId: Int, onDone: () -> Unit, onError: (String) -> Unit) = viewModelScope.launch {
        val amount = txAmount.toDoubleOrNull()
        val price = txPrice.toDoubleOrNull()
        if (amount == null || price == null) {
            onError("Quantidade/Preço inválidos"); return@launch
        }
        try {
            repo.insertTransaction(
                TransactionEntity(
                    coinId = coinId,
                    type = txType,
                    amount = amount,
                    price = price,
                    timestamp = System.currentTimeMillis()
                )
            )
            resetTxForm()
            onDone()
        } catch (e: Exception) {
            onError("Erro ao adicionar transação: ${e.message}")
        }
    }

    fun deleteTransaction(tx: TransactionEntity, onDone: () -> Unit, onError: (String) -> Unit) = viewModelScope.launch {
        try { repo.deleteTransaction(tx); onDone() } catch (e: Exception) { onError("Erro ao excluir: ${e.message}") }
    }

    fun bumpTransactionPrice(tx: TransactionEntity, onDone: () -> Unit, onError: (String) -> Unit) = viewModelScope.launch {
        try { repo.updateTransaction(tx.copy(price = tx.price + 1.0)); onDone() } catch (e: Exception) { onError("Erro ao editar: ${e.message}") }
    }
}
class CryptoViewModelFactory(private val repo: com.example.nubankmvvmmvvm.data.CryptoRepository) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CryptoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CryptoViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
