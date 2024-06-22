package com.example.weatherapp.ui.detailweather.day

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.data.model.Day
import com.example.weatherapp.data.model.Forecastday
import com.example.weatherapp.data.model.Hour
import com.example.weatherapp.databinding.ItemForecastDayBinding
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class ForecastDayAdapter() :
    ListAdapter<Forecastday, ForecastDayAdapter.ForecastDayViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastDayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemForecastDayBinding.inflate(inflater, parent, false)
        return ForecastDayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastDayViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ForecastDayViewHolder(private val binding: ItemForecastDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Forecastday) {
            binding.tvTemp.text = "${data.day.temp.toInt()}°"
            binding.tvDate.text = convertDate(data.time)
            binding.sivIcon.load("https:" + data.day.condition.icon)
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Forecastday>() {
            override fun areItemsTheSame(
                oldItem: Forecastday,
                newItem: Forecastday
            ): Boolean {
                return oldItem.day.temp == newItem.day.temp
            }

            override fun areContentsTheSame(
                oldItem: Forecastday,
                newItem: Forecastday
            ): Boolean {
                return oldItem.time == newItem.time &&
                        oldItem.day.condition.icon == newItem.day.condition.icon

            }
        }
    }

    private fun convertDate(data: Int): String {
        try {
            val dateAPI = DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(data.toLong()))
            val zoneDateTime = ZonedDateTime.parse(dateAPI)
            val localZonedDateTime = zoneDateTime.withZoneSameInstant(ZoneId.of("Asia/Baku"))
            val date = localZonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM").withLocale(Locale("ru_RU")))
            return date

        } catch (e: Exception) {
            val dateAPI = DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(data.toLong()))
            val zoneDateTime = ZonedDateTime.parse(dateAPI)
            val localZonedDateTime = zoneDateTime.withZoneSameInstant(ZoneId.of("Asia/Baku"))
            val date = localZonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM, HH:mm"))
            return date
        }
    }
}