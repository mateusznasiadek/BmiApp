package com.example.myapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.bmi.*
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.history.BmiMeasurement
import com.example.myapp.history.HistoryActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NullPointerException


var HISTORY = ArrayList<BmiMeasurement>()
const val BUNDLE_BMI_VALUE_KEY = "bmi_value"
const val BUNDLE_BMI_OBJECT_KEY = "bmi"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mPrefs: SharedPreferences

    private var amUnit: Boolean = false
    private var lastBmi: Double = 0.0
    private var bmi: Bmi? = null

    private val bmiValueKey: String = "bmi_key"
    private val bmiColorKey: String = "bmi_color_key"
    private val amUnitKey: String = "am_unit_key"
    private val lastBmiKey: String = "last_bmi"
    private val sharedPrefKey: String = "mypref"
    private val bmiObjectKey: String = "bmi_object_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countBTN.setOnClickListener { count(it) }
        bmiTV.setOnClickListener { startBmiInfoActivity(it) }

        mPrefs = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)
        HISTORY = getHistorySharedPref()
    }

    fun startBmiInfoActivity(view: View) {
        if (lastBmi == 0.0) return

        val intent = Intent(this, ActivityBmiInfo::class.java)
        val detailsBundle = Bundle()
        detailsBundle.run {
            putDouble(BUNDLE_BMI_VALUE_KEY, lastBmi)
            putParcelable(BUNDLE_BMI_OBJECT_KEY, bmi)
        }

        intent.putExtra("DETAILS_BUNDLE", detailsBundle)
        startActivityForResult(intent, 1)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putString(bmiValueKey, bmiTV.text.toString())
            putInt(bmiColorKey, bmiTV.currentTextColor)
            putDouble(lastBmiKey, lastBmi)
//            lastBmi?.let { putDouble(lastBmiKey, it) }
            putBoolean(amUnitKey, amUnit)
            if (bmi != null)
                putParcelable(bmiObjectKey, bmi)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            bmiTV.text = getString(bmiValueKey)
            bmiTV.setTextColor(getInt(bmiColorKey))

            lastBmi = getDouble(lastBmiKey)
            amUnit = getBoolean(amUnitKey)
            try {
                bmi = getParcelable(bmiObjectKey)
            } catch (e: NullPointerException) {
            }

            if (amUnit) {
                heightTV.text = getString(R.string.height_in)
                massTV.text = getString(R.string.mass_lb)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveHistoryInSharedPref(HISTORY)
    }

    private fun saveHistoryInSharedPref(list: ArrayList<BmiMeasurement>) {
        val editor = mPrefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(sharedPrefKey, json)
        editor.apply()
    }

    private fun getHistorySharedPref(): ArrayList<BmiMeasurement> {
        val emptyList = Gson().toJson(ArrayList<BmiMeasurement>())
        return Gson().fromJson(
            mPrefs.getString(sharedPrefKey, emptyList),
            object : TypeToken<ArrayList<BmiMeasurement>>() {}.type
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        menu.findItem(R.id.unitSWI).apply {
            isChecked = amUnit
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.unitSWI -> {

                item.isChecked = !item.isChecked

                amUnit = !amUnit
                if (amUnit) {
                    heightTV.text = getString(R.string.height_in)
                    massTV.text = getString(R.string.mass_lb)
                } else {
                    heightTV.text = getString(R.string.height_cm)
                    massTV.text = getString(R.string.mass_kg)
                }

                heightET.text.clear()
                massET.text.clear()
                bmiTV.text = getString(R.string.zero_double)
                bmiTV.setTextColor(getColor(R.color.black))
                lastBmi = 0.0

                true
            }
            R.id.history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun count(view: View) {
        binding.apply {
            val maxWeight: Int
            val minWeight: Int
            val minHeight: Int
            val maxHeight: Int

            if (!amUnit) {
                maxHeight = MAX_HEIGHT_CM
                minHeight = MIN_HEIGHT_CM
                maxWeight = MAX_WEIGHT_KG
                minWeight = MIN_WEIGHT_KG
            } else {
                maxHeight = MAX_HEIGHT_IN
                minHeight = MIN_HEIGHT_IN
                maxWeight = MAX_WEIGHT_LB
                minWeight = MIN_WEIGHT_LB
            }

            when {
                heightET.text.isBlank() -> {
                    heightET.error = getString(R.string.value_is_empty)
                }
                heightET.text.toString().toDouble() > maxHeight || heightET.text.toString()
                    .toDouble() < minHeight -> {
                    heightET.error =
                        getString(R.string.provide_range) + " $minHeight to $maxHeight"
                }
                massET.text.isBlank() -> {
                    massET.error = getString(R.string.value_is_empty)
                }
                massET.text.toString().toDouble() > maxWeight || massET.text.toString()
                    .toDouble() < minWeight -> {
                    massET.error = getString(R.string.provide_range) + " $minWeight to $maxWeight"
                }
                else -> {
                    bmi = if (!amUnit) BmiForCmKg(
                        massET.text.toString().toDouble(),
                        heightET.text.toString().toDouble()
                    )
                    else BmiForInLb(
                        massET.text.toString().toDouble(),
                        heightET.text.toString().toDouble()
                    )

                    val bmiValue: Double = bmi!!.count()
                    lastBmi = bmiValue
                    bmiTV.text = String.format("%.2f", bmiValue)

                    bmi!!.save()

                    changeBMIColor(bmiValue, bmiTV)
                }
            }
        }
    }

    private fun changeBMIColor(bmiValue: Double, textView: TextView) {
        when {
            bmiValue < SEV_UNDERWEIGHT_LIMIT -> {
                textView.setTextColor(getColor(R.color.very_sev_underweight))
            }
            bmiValue < UNDERWEIGHT_LIMIT -> {
                textView.setTextColor(getColor(R.color.sev_underweight))
            }
            bmiValue < NORMAL_LIMIT -> {
                textView.setTextColor(getColor(R.color.underweight))
            }
            bmiValue < OVERWEIGHT_LIMIT -> {
                textView.setTextColor(getColor(R.color.normal_weight))
            }
            bmiValue < OBESE1_LIMIT -> {
                textView.setTextColor(getColor(R.color.overweight))
            }
            bmiValue < OBESE2_LIMIT -> {
                textView.setTextColor(getColor(R.color.obese_1))
            }
            bmiValue < OBESE3_LIMIT -> {
                textView.setTextColor(getColor(R.color.obese_2))
            }
            bmiValue > OBESE3_LIMIT -> {
                textView.setTextColor(getColor(R.color.obese_3))
            }
        }
    }

}

