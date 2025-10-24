package com.example.nucripto.data.local

import androidx.room.*
import com.example.nucripto.data.model.Wallet
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {
    @Query("SELECT * FROM crypto_wallet")
    fun getAll(): Flow<List<Wallet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wallet: Wallet)
}
