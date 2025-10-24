package com.example.nubankmvvmmvvm


import kotlinx.coroutines.flow.Flow

class CryptoRepository(
    private val cryptoDao: CryptoDao,
    private val txDao: TransactionDao
) {
    // Moedas (CRUD 1)
    fun getAllCryptos(): Flow<List<CryptoEntity>> = cryptoDao.getAll()
    suspend fun getCrypto(id: Int): CryptoEntity? = cryptoDao.getById(id)
    suspend fun insertCrypto(c: CryptoEntity): Long = cryptoDao.insert(c)
    suspend fun updateCrypto(c: CryptoEntity) = cryptoDao.update(c)
    suspend fun deleteCrypto(c: CryptoEntity) = cryptoDao.delete(c)

    // Transações (CRUD 2)
    fun getTransactionsFor(coinId: Int): Flow<List<TransactionEntity>> = txDao.getForCoin(coinId)
    suspend fun insertTransaction(tx: TransactionEntity): Long = txDao.insert(tx)
    suspend fun updateTransaction(tx: TransactionEntity) = txDao.update(tx)
    suspend fun deleteTransaction(tx: TransactionEntity) = txDao.delete(tx)
}