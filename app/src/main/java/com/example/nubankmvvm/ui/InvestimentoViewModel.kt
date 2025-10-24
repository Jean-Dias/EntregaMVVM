package com.example.nubankmvvmmvvm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nubankmvvmmvvm.data.Investimento
import com.example.nubankmvvmmvvm.data.InvestimentoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InvestimentoViewModel(private val repo: InvestimentoRepository) : ViewModel() {
    private val _investimentos = MutableStateFlow<List<Investimento>>(emptyList())
    val investimentos: StateFlow<List<Investimento>> = _investimentos.asStateFlow()

    init { loadAll() }

    fun loadAll() {
        viewModelScope.launch {
            _investimentos.value = repo.getAll()
        }
    }

    fun insert(inv: Investimento) {
        viewModelScope.launch {
            repo.insert(inv)
            loadAll()
        }
    }

    fun update(inv: Investimento) {
        viewModelScope.launch {
            repo.update(inv)
            loadAll()
        }
    }

    fun delete(inv: Investimento) {
        viewModelScope.launch {
            repo.delete(inv)
            loadAll()
        }
    }
}

class InvestimentoViewModelFactory(private val repo: InvestimentoRepository) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InvestimentoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InvestimentoViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
