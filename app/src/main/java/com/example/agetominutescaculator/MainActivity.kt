package com.example.agetominutescaculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.agetominutescaculator.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
/*
    private var year = 0
    private var month = 0
    private var day = 0
*/
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDatePicker.setOnClickListener { view ->
            clickDatePicker(view)
        }
    }

    private fun clickDatePicker(view: View) {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DATE)
        val millisecondsToSeconds = 1000
        val milisecondsToMinute = millisecondsToSeconds * 60
        val milisecondsToHour = milisecondsToMinute * 60
        val millsecondsToDays = milisecondsToHour * 24

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                view, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this, "The chosen selectedYear is $selectedYear, the selectedMonth is ${selectedMonth + 1}and " +
                    "the day is $selectedDayOfMonth" , Toast.LENGTH_LONG).show()
            val selectedDate = "$selectedYear/${selectedMonth + 1}/$selectedDayOfMonth"

            binding.tvSelectedDate.setText(selectedDate)

            val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

            val theDate = sdf.parse(selectedDate)
            val selectedDateInDays = theDate!!.time / millsecondsToDays

            // milliSeconds / 1000 = second
            // seconds / 60 = minute
            // minutes / 60 = hour
            // hours / 24 = day
            val selectedDateInMinutes = theDate.time / milisecondsToMinute

            val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

            val currentDateInDays = currentDate!!.time / millsecondsToDays
            val currentDateInMinutes = currentDate.time / milisecondsToMinute

            val differInDays = currentDateInDays - selectedDateInDays
            val differInMinutes = currentDateInMinutes - selectedDateInMinutes
            binding.tvsSelectedDateInDays.setText(differInDays.toString())
            binding.tvsSelectedDateInMinutes.setText(differInMinutes.toString())
        }, year, month, day)

        dpd.datePicker.maxDate = (Date().time - millsecondsToDays)
        dpd.show()
    }

}