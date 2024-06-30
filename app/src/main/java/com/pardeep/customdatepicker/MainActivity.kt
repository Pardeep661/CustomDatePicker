package com.pardeep.customdatepicker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.provider.CalendarContract.Calendars
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import com.pardeep.customdatepicker.databinding.ActivityMainBinding
import java.text.Format
import java.text.SimpleDateFormat
import java.time.Year
import java.util.Calendar
import java.util.Locale
import java.util.SimpleTimeZone

class MainActivity : AppCompatActivity() {

    //declaration of bindng
    var binding: ActivityMainBinding? = null
    var simpleDateFormat = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
    var timeFormat = SimpleDateFormat("HH:mm:ss a", Locale.getDefault())
    private  val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // date picker

        binding?.DatePicker?.setOnClickListener {
            var dialog = DatePickerDialog(
                this, { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, dayOfMonth)
                    val formatData = simpleDateFormat.format(calendar.time)
                    binding?.DatePicker?.setText(formatData)
                },

                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            ).apply {
                //mindate setup
                val todayDate = Calendar.getInstance()
                todayDate.add(Calendar.DAY_OF_MONTH, -10)
                datePicker.minDate = todayDate.timeInMillis

                // maxdate setup
                val todayDate2 = Calendar.getInstance()
                todayDate2.add(Calendar.DAY_OF_MONTH, +10)
                datePicker.maxDate = todayDate2.timeInMillis

                //Theme setup
                setTheme(R.style.Base_Theme_CustomDatePicker)


            }.show()

        }

        binding?.TimePicker?.setOnClickListener {
            TimePickerDialog(
                this, { _, hour, minute ->
                    val calendar = Calendar.getInstance()
                    calendar.set(hour, minute)
                    timeFormat.format(calendar.time)
                },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY,),
                Calendar.getInstance().get(Calendar.MINUTE), false

            )

            .show()

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}