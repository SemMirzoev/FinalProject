package com.example.weatherapp.ui.detailweather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.Constants
import com.example.weatherapp.databinding.FragmentForecastBinding
import com.example.weatherapp.ui.detailweather.day.ForecastDayAdapter
import com.example.weatherapp.ui.detailweather.hour.ForecastHourAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private lateinit var binding: FragmentForecastBinding
    private val forecastHourAdapter =  ForecastHourAdapter()
    private val forecastDayAdapter = ForecastDayAdapter()
    private val viewModel: DetailsViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val city = arguments?.getString("city")
        val time = arguments?.getString("time")

        binding.tvDate.text = time

        initRV()

        lifecycleScope.launch {
            viewModel.getForecast(Constants.API_KEY, city!!, 1)
            viewModel.stateHour.observe(viewLifecycleOwner){
                if (it == null){
                    binding.viewGroup.isVisible = false
                    binding.errorLabel.isVisible = true
                    binding.errorLabel.text = "Unknown error. \nClick to retry"
                }else {
                    forecastHourAdapter.submitList(it)
                }
            }

            viewModel.getDayForecast(Constants.API_KEY,city, 3)
            viewModel.stateDay.observe(viewLifecycleOwner){
                if (it == null){
                    binding.viewGroup.isVisible = false
                    binding.errorLabel.isVisible = true
                    binding.errorLabel.text = "Unknown error. \nClick to retry"
                }else{
                    forecastDayAdapter.submitList(it)
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRV(){
        binding.rvListHour.adapter = forecastHourAdapter
        binding.rvListHour.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.rvListDay.adapter = forecastDayAdapter
        binding.rvListDay.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

    }
}