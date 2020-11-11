package com.example.myapp.bmi

import android.os.Parcelable

interface Bmi :Parcelable {

    fun count(): Double

    fun save()

    fun properWeight(): Pair<Double, Double>
}