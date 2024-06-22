package com.example.weatherapp.ui.detailweather.hour

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.data.model.Hour
import com.example.weatherapp.databinding.ItemForecastBinding
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ForecastHourAdapter() : ListAdapter<Hour, ForecastHourAdapter.ForecastViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemForecastBinding.inflate(inflater, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ForecastViewHolder(private val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Hour) {
            binding.forecastTemperature.text = "${data.temp.toInt()}°"
            binding.forecastHour.text = convertDate(data.time)
            binding.forecastIcon.load("https:" + data.condition.icon)
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Hour>() {
            override fun areItemsTheSame(
                oldItem: Hour,
                newItem: Hour
            ): Boolean {
                return oldItem.temp == newItem.temp
            }

            override fun areContentsTheSame(
                oldItem: Hour,
                newItem: Hour
            ): Boolean {
                return oldItem.time == newItem.time &&
                        oldItem.condition.icon == newItem.condition.icon

            }
        }
    }

    private fun convertDate(data:Int):String {
        try {
            val dateAPI = DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(data.toLong()))
            val zoneDateTime = ZonedDateTime.parse(dateAPI)
            val localZonedDateTime = zoneDateTime.withZoneSameInstant(ZoneId.of("Asia/Baku"))
            val date = localZonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm").withLocale(
                Locale("ru_RU")
            ))
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