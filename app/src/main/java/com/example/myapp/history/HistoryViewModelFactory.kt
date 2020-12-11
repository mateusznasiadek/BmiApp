package com.example.myapp.history

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.database.BmiDatabaseDao

class HistoryViewModelFactory(
    private val database: BmiDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}