package com.example.opsc_poe_part2_timewise

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.opsc_poe_part2_timewise.databinding.ActivityTimesheetDetailsBinding

class TimesheetDetails : AppCompatActivity() {
    private lateinit var binding:ActivityTimesheetDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityTimesheetDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val receivedName = intent.getStringExtra("name")
        val receivedDate = intent.getStringExtra("date")
        val receivedCategory = intent.getStringExtra("category")
        val receivedDescription = intent.getStringExtra("description")
        val receivedTimeSpent = intent.getStringExtra("timeSpent")
        val receivedImage = intent.getStringExtra("image")
        binding.nameTV.text = receivedName.toString()
        binding.dateTV.text = receivedDate.toString()
        binding.categoryTV.text = receivedCategory.toString()
        binding.descriptionTV.text = receivedDescription.toString()
        binding.timeSpentTV.text = receivedTimeSpent.toString()
        binding.entryImageView.setImageURI(Uri.parse(receivedImage))

        binding.returnBtn.setOnClickListener{
            var intent = Intent(this,TimesheetEntriesListDisplay::class.java)
            startActivity(intent)
            finish()
        }



    }
}