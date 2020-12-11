package com.example.myapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BmiDatabaseDao {

    @Insert
    suspend fun insert(bmi: BmiMeasurement)

    @Update
    suspend fun update(bmi: BmiMeasurement)

    @Query("SELECT * from bmi_measurements_table WHERE bmiId = :key")
    suspend fun get(key: Long): BmiMeasurement

    @Query("DELETE FROM bmi_measurements_table")
    suspend fun clear()

    @Query("SELECT * FROM bmi_measurements_table ORDER BY bmiId DESC LIMIT 1")
    suspend fun getLast(): BmiMeasurement?

    @Query("SELECT * FROM bmi_measurements_table ORDER BY bmiId DESC")
    fun getAllBmiMeasurements(): List<BmiMeasurement>
}