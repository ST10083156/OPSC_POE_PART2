package com.example.opsc_poe_part2_timewise

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.opsc_poe_part2_timewise.databinding.ActivityCategoriesDisplayBinding
import kotlin.time.Duration

class CategoriesDisplay : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoriesDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var totalMinutes : Duration = Duration.ZERO
        var totalCategoryTimes = mutableListOf<Duration>()
        for(entry in TimesheetEntriesList.getEntriesList())
        {
            for(category in CategoriesList.getCategoriesList())
            {
                if(entry.Category==category)
                {
                    totalMinutes= totalMinutes + entry.TimeSpent
                }
                totalCategoryTimes.add(totalMinutes)
                totalMinutes = Duration.ZERO

            }

        }

var container = binding.categoryContainer

        for(index in CategoriesList.getCategoriesList().indices)
        {
            var textView = TextView(this)
            textView.text="Category: ${CategoriesList.getCategoriesList()[index].Name} - ${totalCategoryTimes[index].inWholeMilliseconds/1000/60} Minutes"
            textView.textSize = 24f
            container.addView(textView)
        }

        binding.returnBtn.setOnClickListener{
            var intent = Intent(this,TimesheetEntriesListDisplay::class.java)
            startActivity(intent)
            finish()
        }
    }
}