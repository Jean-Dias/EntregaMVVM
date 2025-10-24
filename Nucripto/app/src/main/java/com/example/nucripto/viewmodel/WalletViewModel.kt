package com.example.nucripto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nucripto.data.model.Wallet
import com.example.nucripto.data.repository.WalletRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WalletViewModel(private val repository: WalletRepository) : ViewModel() {

    val wallets: StateFlow<List<Wallet>> = repository.wallets.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun addWallet(wallet: Wallet) = viewModelScope.launch {
        repository.addWallet(wallet)
    }
}
