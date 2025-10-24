package com.example.nucripto.data.repository

import com.example.nucripto.data.model.Wallet
import com.example.nucripto.data.local.WalletDao
import kotlinx.coroutines.flow.Flow

class WalletRepository(private val dao: WalletDao) {
    val wallets: Flow<List<Wallet>> = dao.getAll()

    suspend fun addWallet(wallet: Wallet) = dao.insert(wallet)
}
