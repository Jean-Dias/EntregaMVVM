package com.example.nubankmvvmmvvm.data

import kotlinx.coroutines.flow.Flow

class InvestimentoRepository(private val investimentoDao: InvestimentoDao) {
    fun getAllInvestimentos(): Flow<List<Investimento>> = investimentoDao.getAllFlow()
    suspend fun getAll(): List<Investimento> = investimentoDao.getAll()
    suspend fun insert(investimento: Investimento) = investimentoDao.insert(investimento)
    suspend fun update(investimento: Investimento) = investimentoDao.update(investimento)
    suspend fun delete(investimento: Investimento) = investimentoDao.delete(investimento)
}
