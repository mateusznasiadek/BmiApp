package com.example.myapp.history

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import com.example.myapp.HISTORY
import kotlinx.android.synthetic.main.bmi_history.*


class HistoryActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi_history)

        val adapter = BmiMeasurementAdapter(HISTORY)
        historyRV.adapter = adapter
        historyRV.layoutManager = LinearLayoutManager(this)
    }
}