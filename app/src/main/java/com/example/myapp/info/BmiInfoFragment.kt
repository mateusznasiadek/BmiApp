package com.example.myapp.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.R
import com.example.myapp.bmi.*
import com.example.myapp.databinding.FragmentBmiInfoBinding

class BmiInfoFragment : Fragment() {

    private lateinit var binding: FragmentBmiInfoBinding
    private lateinit var viewModelFactory: BmiInfoViewModelFactory
    private lateinit var viewModel: BmiInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_bmi_info,
            container,
            false
        )

        viewModelFactory =
            BmiInfoViewModelFactory(BmiInfoFragmentArgs.fromBundle(requireArguments()).bmi)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BmiInfoViewModel::class.java)

        binding.bmiInfoViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setTextColor(viewModel.bmi.count())

        return binding.root
    }

    private fun setTextColor(bmiValue: Double?) {
        if (bmiValue != null) {
            binding.bmiValTV.setTextColor(
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

}