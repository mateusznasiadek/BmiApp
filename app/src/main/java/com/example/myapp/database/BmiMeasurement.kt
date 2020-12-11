package com.example.myapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bmi_measurements_table")
data class BmiMeasurement(
    @PrimaryKey(autoGenerate = true)
    val bmiId: Long = 0L,

    @ColumnInfo(name = "bmi")
    val bmi: Double,

    @ColumnInfo(name = "height")
    val height: String,

    @ColumnInfo(name = "mass")
    val mass: String,

    @ColumnInfo(name = "date")
    val date: String
)