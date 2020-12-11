package com.example.myapp.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDateTime


class LocalDateTimeAttributeConverter {

    @TypeConverter
    fun toString(value: LocalDateTime): String = value.toString()

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromString(value: String): LocalDateTime? =
        if (value.isEmpty()) null else objectFromString(value)

    @RequiresApi(Build.VERSION_CODES.O)
    fun objectFromString(value: String): LocalDateTime? = LocalDateTime.parse(value)
}