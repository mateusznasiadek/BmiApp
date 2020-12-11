package com.example.myapp.history

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.database.BmiMeasurement
import kotlinx.android.synthetic.main.history_item.view.*
import java.time.format.DateTimeFormatter

class HistoryRecyclerViewAdapter(
    private var measurements: List<BmiMeasurement>
) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.MeasurementViewHolder>() {
    inner class MeasurementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasurementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return MeasurementViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MeasurementViewHolder, position: Int) {
        holder.itemView.apply {
            historyBmiValTV.text = String.format("%.2f", measurements[position].bmi)
            historyHeightTV.text = measurements[position].height
            historyMassTV.text = measurements[position].mass
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            historyDateTV.text = measurements[position].date.format(formatter)
        }
    }

    override fun getItemCount(): Int {
        return measurements.size
    }

}