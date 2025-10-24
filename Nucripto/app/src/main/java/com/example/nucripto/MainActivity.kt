package com.example.nucripto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.room.Room
import com.example.nucripto.data.local.AppDatabase
import com.example.nucripto.data.repository.WalletRepository
import com.example.nucripto.ui.NavGraph
import com.example.nucripto.ui.NuCriptoTheme
import com.example.nucripto.viewmodel.WalletViewModel

private fun MainActivity.Surface(color: Color, content: () -> Unit) {
    TODO("Not yet implemented")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o Room Database
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "nucripto-db"
        ).build()

        // Cria o repositório
        val repository = WalletRepository(db.walletDao())

        // Cria o ViewModel
        val viewModel = WalletViewModel(repository)

        setContent {
            NuCriptoTheme() {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavGraph(viewModel)
                }
            }
        }
    }

    private fun setContent(content: () -> NuCriptoTheme) {
            TODO("Not yet implemented")
    }


