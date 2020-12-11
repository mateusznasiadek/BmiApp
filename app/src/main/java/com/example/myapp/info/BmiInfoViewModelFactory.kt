package com.example.myapp.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.bmi.Bmi

class BmiInfoViewModelFactory(private val bmi: Bmi) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BmiInfoViewModel::class.java)) {
            return BmiInfoViewModel(bmi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}