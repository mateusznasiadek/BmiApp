package com.example.myapp.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.myapp.database.BmiDatabaseDao
import com.example.myapp.database.BmiMeasurement

class HistoryViewModel(
    database: BmiDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val _bmiHistory = database.getAllBmiMeasurements()
    val bmiHistory: List<BmiMeasurement>
        get() = _bmiHistory
}