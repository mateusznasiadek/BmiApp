package com.example.myapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.bmi.*
import kotlinx.android.synthetic.main.activity_bmi_info.*

class ActivityBmiInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_info)

        val bundle: Bundle? = intent.getBundleExtra("DETAILS_BUNDLE")
        val bmiValue: Double? = bundle?.getDouble(BUNDLE_BMI_VALUE_KEY)
        val bmi: Bmi? = bundle?.getParcelable(BUNDLE_BMI_OBJECT_KEY)

        val textView = findViewById<TextView>(R.id.bmiValTV).apply {
            text = String.format("%.2f", bmiValue)
        }

        findViewById<TextView>(R.id.properWeightTV).apply {
            if (bmi != null) {
                val range =
                    String.format("%.1f", bmi.properWeight().first) + " to " + String.format(
                        "%.1f", bmi.properWeight().second
                    )
                text = range
            }
        }


        if (bmiValue != null) {
            when {
                bmiValue < SEV_UNDERWEIGHT_LIMIT -> {
                    textView.setTextColor(getColor(R.color.very_sev_underweight))
                    bmiDescTV.text = getString(R.string.bmi_info_very_sev_und)
                }
                bmiValue < UNDERWEIGHT_LIMIT -> {
                    textView.setTextColor(getColor(R.color.sev_underweight))
                    bmiDescTV.text = getString(R.string.bmi_info_sev_und)
                }
                bmiValue < NORMAL_LIMIT -> {
                    textView.setTextColor(getColor(R.color.underweight))
                    bmiDescTV.text = getString(R.string.bmi_info_und)
                }
                bmiValue < OVERWEIGHT_LIMIT -> {
                    textView.setTextColor(getColor(R.color.normal_weight))
                    bmiDescTV.text = getString(R.string.bmi_info_normal)
                }
                bmiValue < OBESE1_LIMIT -> {
                    textView.setTextColor(getColor(R.color.overweight))
                    bmiDescTV.text = getString(R.string.bmi_info_over)
                }
                bmiValue < OBESE2_LIMIT -> {
                    textView.setTextColor(getColor(R.color.obese_1))
                    bmiDescTV.text = getString(R.string.bmi_info_ob1)
                }
                bmiValue < OBESE3_LIMIT -> {
                    textView.setTextColor(getColor(R.color.obese_2))
                    bmiDescTV.text = getString(R.string.bmi_info_ob2)
                }
                bmiValue > OBESE3_LIMIT -> {
                    textView.setTextColor(getColor(R.color.obese_3))
                    bmiDescTV.text = getString(R.string.bmi_info_ob3)
                }
            }
        }
    }

}