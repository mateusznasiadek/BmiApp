package com.example.myapp.bmi

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.myapp.database.BmiMeasurement
import com.example.myapp.database.LocalDateTimeAttributeConverter
import java.time.LocalDateTime

class BmiForInLb(
    private val mass: Double,
    private val height: Double
) : Bmi {

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun count(): Double {
        if (mass < MIN_WEIGHT_LB || mass > MAX_WEIGHT_LB || height < MIN_HEIGHT_IN || height > MAX_HEIGHT_IN) throw IllegalArgumentException()
        else return mass / (height * height) * 703
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun toBmiMeasurement() : BmiMeasurement{
        val feet: Int = (height / 12).toInt()
        val inches: Int = (height % 12).toInt()
        val converter = LocalDateTimeAttributeConverter()

        return BmiMeasurement(
            0L,
            count(),
            "$feet' $inches\"",
            String.format("%.1f", mass) + " lb",
            converter.toString(LocalDateTime.now())
        )

    }

    override fun properWeight(): Pair<Double, Double> {
        val minWeight = NORMAL_LIMIT * height * height / 703
        val maxWeight = OVERWEIGHT_LIMIT * height * height / 703
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

    companion object CREATOR : Parcelable.Creator<BmiForInLb> {
        override fun createFromParcel(parcel: Parcel): BmiForInLb {
            return BmiForInLb(parcel)
        }

        override fun newArray(size: Int): Array<BmiForInLb?> {
            return arrayOfNulls(size)
        }
    }
}
