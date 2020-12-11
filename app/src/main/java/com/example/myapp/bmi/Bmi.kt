package com.example.myapp.bmi

import android.os.Parcelable
import com.example.myapp.database.BmiMeasurement

interface Bmi : Parcelable{

    fun count(): Double

    fun toBmiMeasurement() : BmiMeasurement

    fun properWeight(): Pair<Double, Double>
}