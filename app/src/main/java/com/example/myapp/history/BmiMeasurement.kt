package com.example.myapp.history

import java.time.LocalDateTime

data class BmiMeasurement(
    val bmi: Double,
    val height: String,
    val mass: String,
    val date: LocalDateTime
)