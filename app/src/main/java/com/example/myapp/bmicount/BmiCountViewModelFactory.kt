package com.example.myapp.bmicount

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.database.BmiDatabaseDao

class BmiCountViewModelFactory(
    private val database: BmiDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BmiCountViewModel::class.java)) {
            return BmiCountViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}