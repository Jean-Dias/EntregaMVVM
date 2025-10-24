package com.example.nubankmvvmmvvm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CryptoEntity::class, TransactionEntity::class, Investimento::class], version = 1, exportSchema = false)
abstract class NubankDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao
    abstract fun transactionDao(): TransactionDao
    abstract fun investimentoDao(): InvestimentoDao

    companion object {
        @Volatile
        private var INSTANCE: NubankDatabase? = null

        fun getDatabase(context: Context): NubankDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NubankDatabase::class.java,
                    "nubank_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
