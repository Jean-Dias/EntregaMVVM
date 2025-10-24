package com.example.nubankmvvmmvvm

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CryptoEntity::class,
            parentColumns = ["id"],
            childColumns = ["coinId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["coinId"])]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val coinId: Int,
    val type: String, // "BUY" ou "SELL"
    val amount: Double,
    val price: Double,
    val timestamp: Long
)