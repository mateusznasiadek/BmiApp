package com.example.myapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BmiMeasurement::class], version = 1, exportSchema = false)
abstract class BmiDatabase : RoomDatabase() {

    abstract val bmiDatabaseDao: BmiDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: BmiDatabase? = null

        fun getInstance(context: Context): BmiDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BmiDatabase::class.java,
                        "bmi_history_database"
                    )
                        .allowMainThreadQueries() //NW CZY TO MOZE BYC
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}