package com.example.nubankmvvmmvvm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CarteiraTransacao")
data class CarteiraTransacao(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cryptoId: Long,
    val assetSymbol: String,
    val quantityFiat: Double,
    val unitPrice: Double,
    val timestamp: Long = System.currentTimeMillis()
)
