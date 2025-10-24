package com.example.nubankmvvmmvvm


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {
    @Query("SELECT * FROM cryptos ORDER BY name ASC")
    fun getAll(): Flow<List<CryptoEntity>>

    @Query("SELECT * FROM cryptos WHERE id = :id")
    suspend fun getById(id: Int): CryptoEntity?

    @Insert
    suspend fun insert(crypto: CryptoEntity): Long

    @Update
    suspend fun update(crypto: CryptoEntity)

    @Delete
    suspend fun delete(crypto: CryptoEntity)
}