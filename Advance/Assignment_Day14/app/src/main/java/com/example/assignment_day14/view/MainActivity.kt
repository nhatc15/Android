package com.example.assignment_day14.view

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.assignment_day14.R
import com.example.assignment_day14.databinding.ActivityMainBinding
import com.example.assignment_day14.model.WeatherModel
import com.example.assignment_day14.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        var cName = GET.getString("cityName","HaNoi")
        binding.etCityName.setText(cName)

        viewModel.refreshData(cName!!)

        getLiveData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            with(binding){
                llDataView.visibility = View.GONE
                pbLoading.visibility = View.GONE
                tvError.visibility = View.GONE

                var cityName = GET.getString("cityName",cName)
                etCityName.setText(cityName)
                viewModel.refreshData(cityName!!)
                swipeRefreshLayout.isRefreshing = false
            }
        }

        binding.imgSearchCityName.setOnClickListener {
            val cityName = binding.etCityName.text.toString()
            SET.putString("cityName",cityName)
            SET.apply()
            viewModel.refreshData(cityName)
            getLiveData()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getLiveData() {
        viewModel.weather_data.observe(this, Observer { data ->
            data?.let {
                with(binding){
                    llDataView.visibility = View.VISIBLE
                    pbLoading.visibility = View.GONE
                    tvDegree.text = data.main.temp.toString() + "℃"
                    tvCountryCode.text = data.sys.country
                    tvCityName.text = data.name

                    tvHumidity.text = data.main.humidity.toString()
                    tvSpeed.text = data.wind.speed.toString() + "%"
                    tvLat.text = data.coord.lat.toString()
                    tvLon.text = data.coord.lon.toString()

                    Glide.with(this@MainActivity)
                        .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon+ "@2x.png")
                        .into(imgWeatherIcon)
                }

            }
        })

        viewModel.weather_load.observe(this, Observer { load->
            load?.let{
                if(it){
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    binding.llDataView.visibility = View.GONE
                }else{
                    binding.pbLoading.visibility = View.GONE
                }
            }
        })

        viewModel.weather_error.observe(this, Observer { error->
            error?.let{
                if(it){
                    binding.pbLoading.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.llDataView.visibility = View.GONE
                }else{
                    binding.tvError.visibility = View.GONE
                }
            }
        })
    }
}