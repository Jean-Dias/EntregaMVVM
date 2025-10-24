package com.example.nubankmvvmmvvm


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptos")
data class CryptoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val symbol: String,
    val price: Double,
    val variation: Double
)
