package com.example.myapp.bmicount

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapp.bmi.Bmi
import com.example.myapp.bmi.BmiForCmKg
import com.example.myapp.bmi.BmiForInLb
import com.example.myapp.database.BmiDatabaseDao
import com.example.myapp.database.BmiMeasurement
import kotlinx.coroutines.launch

class BmiCountViewModel(
    private val database: BmiDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val _bmi = MutableLiveData<Bmi?>()
    val bmi: MutableLiveData<Bmi?>
        get() = _bmi

    private val _amUnit = MutableLiveData<Boolean>()
    val amUnit: MutableLiveData<Boolean>
        get() = _amUnit


    init {
        _amUnit.value = false
        _bmi.value = null
    }


    fun changeUnits() {
        _amUnit.value = !_amUnit.value!!
        _bmi.value = null
    }

    fun count() {
        viewModelScope.launch {

            val newBmi = _bmi.value?.toBmiMeasurement()

            if (newBmi != null) {
                insert(newBmi)
            }
        }
    }

    private suspend fun insert(bmi: BmiMeasurement) {
        database.insert(bmi)
    }

    private suspend fun update(bmi: BmiMeasurement) {
        database.update(bmi)
    }

    private suspend fun clear() {
        database.clear()
    }
}