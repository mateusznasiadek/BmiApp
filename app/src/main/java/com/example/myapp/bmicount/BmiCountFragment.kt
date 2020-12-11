package com.example.myapp.bmicount

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.myapp.R
import com.example.myapp.bmi.*
import com.example.myapp.database.BmiDatabase
import com.example.myapp.databinding.FragmentCountBinding

class BmiCountFragment : Fragment() {

    private lateinit var binding: FragmentCountBinding
    private lateinit var viewModel: BmiCountViewModel
    private lateinit var viewModelFactory: BmiCountViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_count,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = BmiDatabase.getInstance(application).bmiDatabaseDao
        viewModelFactory = BmiCountViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BmiCountViewModel::class.java)

        setHasOptionsMenu(true)

        binding.bmiCountViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.amUnit.observe(viewLifecycleOwner, {
            if (it == true) {
                binding.heightTV.text = getString(R.string.height_in)
                binding.massTV.text = getString(R.string.mass_lb)
            } else {
                binding.heightTV.text = getString(R.string.height_cm)
                binding.massTV.text = getString(R.string.mass_kg)
            }
            binding.heightET.text.clear()
            binding.massET.text.clear()
            binding.bmiTV.text = getString(R.string.zero_double)
            binding.bmiTV.setTextColor(resources.getColor(R.color.black, null))
        })

        binding.countBTN.setOnClickListener { count() }
        binding.bmiTV.setOnClickListener { startBmiInfoActivity() }

        return binding.root
    }

    private fun startBmiInfoActivity() {
        val bmi = viewModel.bmi.value
        if (bmi != null) {
            val action = BmiCountFragmentDirections.actionBmiCountFragmentToBmiInfoFragment(bmi)
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        menu.findItem(R.id.unitSWI).apply {
            isChecked = viewModel.amUnit.value!!
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.unitSWI -> {
                item.isChecked = !item.isChecked
                viewModel.changeUnits()
                true
            }
            R.id.history -> {
                val action = BmiCountFragmentDirections.actionBmiCountFragmentToHistoryFragment()
                NavHostFragment.findNavController(this).navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun count() {
        val maxWeight: Int
        val minWeight: Int
        val minHeight: Int
        val maxHeight: Int

        if (!viewModel.amUnit.value!!) {
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

        binding.apply {
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
                    viewModel.bmi.value = if (!viewModel.amUnit.value!!) BmiForCmKg(
                        massET.text.toString().toDouble(),
                        heightET.text.toString().toDouble()
                    )
                    else BmiForInLb(
                        massET.text.toString().toDouble(),
                        heightET.text.toString().toDouble()
                    )

                    val bmiValue: Double = viewModel.bmi.value!!.count()
                    bmiTV.text = String.format("%.2f", bmiValue)
                    changeBMIColor(bmiValue)

                    /***
                    //ZAPISANIE DO BAZY
                     */

                    viewModel.count()
                }
            }
        }
    }

    private fun changeBMIColor(bmiValue: Double) {
        binding.bmiTV.setTextColor(
            resources.getColor(
                when {
                    bmiValue < SEV_UNDERWEIGHT_LIMIT -> R.color.very_sev_underweight
                    bmiValue < UNDERWEIGHT_LIMIT -> R.color.sev_underweight
                    bmiValue < NORMAL_LIMIT -> R.color.underweight
                    bmiValue < OVERWEIGHT_LIMIT -> R.color.normal_weight
                    bmiValue < OBESE1_LIMIT -> R.color.overweight
                    bmiValue < OBESE2_LIMIT -> R.color.obese_1
                    bmiValue < OBESE3_LIMIT -> R.color.obese_2
                    bmiValue > OBESE3_LIMIT -> R.color.obese_3

                    else -> R.color.black
                }, null
            )
        )
    }
}


