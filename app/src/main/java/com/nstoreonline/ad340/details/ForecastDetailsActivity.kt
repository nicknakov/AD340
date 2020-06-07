package com.nstoreonline.ad340.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import com.nstoreonline.ad340.*

class ForecastDetailsActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)
        // sets title of the activity
        setTitle(R.string.forecast_details)

        val tempText = findViewById<TextView>(R.id.tempText)
        val descText = findViewById<TextView>(R.id.descriptionText)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

        /*val temp = intent.getFloatExtra("key_temp", 0f)
        val description = intent.getStringExtra("key_description")*/
        val temp = intent.getFloatExtra("key_temp", 0f)
        tempText.text = formatTempForDisplay(temp, tempDisplaySettingManager.getTempDisplaySetting())
        descText.text = intent.getStringExtra("key_description")
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
