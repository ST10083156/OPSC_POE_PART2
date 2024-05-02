package com.example.opsc_poe_part2_timewise

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.opsc_poe_part2_timewise.databinding.ActivityChangeTimePeriodBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class ChangeTimePeriod : AppCompatActivity() {
    private lateinit var binding:ActivityChangeTimePeriodBinding
    private lateinit var startDate:LocalDate
    private lateinit var endDate:LocalDate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChangeTimePeriodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.setBtn.setOnClickListener{
            if(convertTextToDate(binding.earlyDateET.text.toString()).isBefore(convertTextToDate(binding.lateDateET.text.toString())))
            {
                startDate = convertTextToDate(binding.earlyDateET.text.toString())
                endDate = convertTextToDate(binding.lateDateET.text.toString())
                var intent = Intent(this,TimesheetEntriesListDisplay::class.java)
                intent.putExtra("startDate",startDate.toString())
                intent.putExtra("endDate", endDate.toString())
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Please ensure all fields are filled correctly and are valid",Toast.LENGTH_LONG)
            }
        }
    }

    fun convertTextToDate(inputText: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = LocalDate.parse(inputText,formatter)
        return date
    }
}