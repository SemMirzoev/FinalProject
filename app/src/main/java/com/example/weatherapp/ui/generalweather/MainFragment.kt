package com.example.weatherapp.ui.generalweather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        viewModel.state.observe(viewLifecycleOwner){
            if (it == null) {
                binding.viewsGroup.isVisible = false
                binding.errorLabel.isVisible = true
                binding.errorLabel.text = "Click to retry"
                Toast.makeText(requireContext(), "Unknown error", Toast.LENGTH_LONG).show()
                binding.errorLabel.setOnClickListener{
                    getData()
                }
            }
            it?.let {
                binding.apply {
                    viewsGroup.isVisible = true
                    binding.errorLabel.isVisible = false
                    cityName.text = "${it.location.country}, ${it.location.city}"
                    weatherIcon.load("https:" + it.current.condition.icon)
                    temperature.text = "${it.current.temp.toInt()}°"
                    weatherDescription.text = it.current.condition.text
                    wind.text = "Wind   |   ${it.current.wind} km/h"
                    humidity.text = "Hum   |   ${it.current.hum} %"
                    CityData.text = convertDate(it.location.time)
                }
            }
        }

        binding.forecastButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("city", binding.cityName.text.toString())
            bundle.putString("time",binding.CityData.text.toString())
            findNavController().navigate(R.id.action_mainFragment_to_forecastFragment, bundle)
        }

        binding.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                lifecycleScope.launch {
                    viewModel.getWeather(parent?.getItemAtPosition(position).toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(), "Город не выбран!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getData(){
        lifecycleScope.launch {
            val city = binding.citySpinner.selectedItem.toString()
            viewModel.getWeather(city)
        }
    }

    private fun convertDate(data:Int):String {
        try {
            val dateAPI = DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(data.toLong()))
            val zoneDateTime = ZonedDateTime.parse(dateAPI)
            val localZonedDateTime = zoneDateTime.withZoneSameInstant(ZoneId.of("Asia/Baku"))
            val date = localZonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM, HH:mm").withLocale(Locale("ru_RU")))
            return date

        }catch (e:Exception){
            val dateAPI = DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(data.toLong()))
            val zoneDateTime = ZonedDateTime.parse(dateAPI)
            val localZonedDateTime = zoneDateTime.withZoneSameInstant(ZoneId.of("Asia/Baku"))
            val date = localZonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM, HH:mm"))
            return date
        }
    }


}