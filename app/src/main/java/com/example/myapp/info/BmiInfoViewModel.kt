package com.example.myapp.info

import androidx.lifecycle.ViewModel
import com.example.myapp.bmi.*

class BmiInfoViewModel(bmi: Bmi) : ViewModel() {

    private val _bmi: Bmi = bmi
    val bmi: Bmi
        get() = _bmi

    private val bmiValue = _bmi.count()
    val bmiValueString: String = String.format("%.2f", bmiValue)

    val properWeight: String =
        String.format("%.1f", bmi.properWeight().first) + " to " + String.format(
            "%.1f",
            bmi.properWeight().second
        )

    val weightDesc: String =
        when {
            bmiValue < SEV_UNDERWEIGHT_LIMIT -> "You are very severely underweight. You should put on some weight."
            bmiValue < UNDERWEIGHT_LIMIT -> "You are severely underweight. You should put on some weight."
            bmiValue < NORMAL_LIMIT -> "You are underweight. You should put on some weight."
            bmiValue < OVERWEIGHT_LIMIT -> "Your weight is normal and healthy."
            bmiValue < OBESE1_LIMIT -> "You are overweight. You should lose some weight."
            bmiValue < OBESE2_LIMIT -> "You are obese. You should lose some weight."
            bmiValue < OBESE3_LIMIT -> "You are severely obese. You should lose some weight."
            bmiValue > OBESE3_LIMIT -> "You are very severely obese. You should lose some weight."
            else -> ""
        }
}
