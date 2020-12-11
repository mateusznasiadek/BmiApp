package com.example.myapp.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapp.R
import com.example.myapp.bmicount.BmiCountViewModel
import com.example.myapp.bmicount.BmiCountViewModelFactory
import com.example.myapp.database.BmiDatabase
import com.example.myapp.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var viewModelFactory: HistoryViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_history,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = BmiDatabase.getInstance(application).bmiDatabaseDao
        viewModelFactory = HistoryViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java)


        val history = viewModel.bmiHistory
        binding.historyRV.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = HistoryRecyclerViewAdapter(history)
        }

        return binding.root
    }
}