package com.example.nubankmvvmmvvm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "investimentos")
data class Investimento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val valor: Double
)
