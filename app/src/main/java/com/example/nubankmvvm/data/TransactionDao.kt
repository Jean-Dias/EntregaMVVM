package com.example.nubankmvvmmvvm



import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE coinId = :coinId ORDER BY timestamp DESC")
    fun getForCoin(coinId: Int): Flow<List<TransactionEntity>>

    @Insert
    suspend fun insert(tx: TransactionEntity): Long

    @Update
    suspend fun update(tx: TransactionEntity)

    @Delete
    suspend fun delete(tx: TransactionEntity)
}