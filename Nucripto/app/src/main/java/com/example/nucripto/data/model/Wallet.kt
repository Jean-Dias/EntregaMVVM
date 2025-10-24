package com.example.nucripto.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_wallet")
data class Wallet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val balance: Double
)
