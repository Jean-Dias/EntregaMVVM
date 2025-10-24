package com.example.nucripto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nucripto.data.model.Wallet

@Database(entities = [Wallet::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
}
