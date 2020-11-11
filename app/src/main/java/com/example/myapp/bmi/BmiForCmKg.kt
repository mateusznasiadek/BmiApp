package com.example.myapp.bmi

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.myapp.HISTORY
import com.example.myapp.history.BmiMeasurement
import java.time.LocalDateTime
import kotlin.IllegalArgumentException
import kotlin.math.min

open class BmiForCmKg(
    private val mass: Double,
    private val height: Double
) : Bmi {

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun count(): Double {
        if (mass < MIN_WEIGHT_KG || mass > MAX_WEIGHT_KG || height < MIN_HEIGHT_CM || height > MAX_HEIGHT_CM) throw IllegalArgumentException()
        else return mass / (height * height / 10000)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun save() {
        val bmi = BmiMeasurement(
            count(),
            String.format("%.1f", height) + " cm",
            String.format("%.1f", mass) + " kg",
            LocalDateTime.now()
        )

        HISTORY.add(0, bmi)
        if (HISTORY.size > 10)
            HISTORY = HISTORY.take(10) as ArrayList<BmiMeasurement>
    }

    override fun properWeight(): Pair<Double, Double> {
        val minWeight = NORMAL_LIMIT * height * height / 10000
        val maxWeight = OVERWEIGHT_LIMIT * height * height / 10000
        return Pair(minWeight, maxWeight)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        super.writeToParcel(parcel, flags)
        parcel.writeDouble(mass)
        parcel.writeDouble(height)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BmiForCmKg> {
        override fun createFromParcel(parcel: Parcel): BmiForCmKg {
            return BmiForCmKg(parcel)
        }

        override fun newArray(size: Int): Array<BmiForCmKg?> {
            return arrayOfNulls(size)
        }
    }
}
