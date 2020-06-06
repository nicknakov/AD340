package com.nstoreonline.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {

    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast

    fun loadForecast(zipcode: String){
        val randomValues = List(10){Random.nextFloat().rem(100) * 100 }
        val forecastItems = randomValues.map { temp ->
            DailyForecast(temp, getTempDescription(temp))
        }
        _weeklyForecast.value = forecastItems
    }

    private fun getTempDescription(temp: Float) : String {
        //return if (temp < 75) "It's too cold" else "It's great"
        return when (temp) {
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 does not make sense"
            in 0f.rangeTo(32f) -> "Way too cold"
            in 32f.rangeTo(55f) -> "Colder than expected"
            in 55f.rangeTo(65f) -> "Getting better"
            in 65f.rangeTo(80f) -> "That th sweet spot"
            in 80f.rangeTo(90f) -> "Getting a little worm!"
            in 90f.rangeTo(100f) -> "Where is the A/C?"
            in 100f.rangeTo(Float.MAX_VALUE) -> "What is this... Arizona?"
            else -> "Does not compute"
        }
    }
}