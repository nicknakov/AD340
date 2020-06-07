package com.nstoreonline.ad340

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nstoreonline.ad340.details.ForecastDetailsActivity

class MainActivity : AppCompatActivity() {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)
        val enterButton: Button = findViewById(R.id.enterButton)

        enterButton.setOnClickListener {
            val zipcode: String = zipcodeEditText.text.toString()
            if (zipcode.length != 5){
                Toast.makeText(this, R.string.zipcode_entry_error, Toast.LENGTH_SHORT).show()
            }else {
                forecastRepository.loadForecast(zipcode)
            }
        }

        // region Recycler View Setup
        val forecastList: RecyclerView = findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(this)
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){forecastItem ->
            //val message = getString(R.string.forecast_clicked_format, forecastItem.temp, forecastItem.description)
            showForecastDetails(forecastItem)
        }
        forecastList.adapter = dailyForecastAdapter
        //endregion

        // region Observer for changes
        val weeklyForecastObserver = Observer<List<DailyForecast>> {forecastItems ->
            // update our list adapter
            dailyForecastAdapter.submitList(forecastItems)
        }
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)
        // endregion
    }

    private fun showForecastDetails(forecast: DailyForecast){
        val forecastDetailIntent = Intent(this, ForecastDetailsActivity::class.java)
        //pass data to new activity
        forecastDetailIntent.putExtra("key_temp", forecast.temp)
        forecastDetailIntent.putExtra("key_description", forecast.description)
        startActivity(forecastDetailIntent)
    }

    // region Menu Settings Config
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when(item.itemId){
            R.id.tempDisplaySettings ->{
                showTempDisplaySettingsDialog(this, tempDisplaySettingManager)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    // endregion
}
