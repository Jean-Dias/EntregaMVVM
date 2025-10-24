package com.example.nubankmvvmmvvm

import androidx.room.*
import androidx.room.Dao



@Dao
interface InvestimentoDao {
    @Query("SELECT * FROM investimentos")
    suspend fun getAll(): List<Investimento>

    @Insert
    suspend fun insert(investimento: Investimento)

    @Update
    suspend fun update(investimento: Investimento)

    @Delete
    suspend fun delete(investimento: Investimento)
}
