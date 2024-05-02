package com.example.opsc_poe_part2_timewise

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.opsc_poe_part2_timewise.databinding.ActivitySetGoalsBinding

class SetGoals : AppCompatActivity() {
    private lateinit var binding :ActivitySetGoalsBinding
    private lateinit var minGoal : Number
    private lateinit var maxGoal : Number

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySetGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.setBtn.setOnClickListener{
            if(binding.minET.text.toString().toInt()>binding.maxET.text.toString().toInt()){
                Toast.makeText(this,"Please ensure max goal is greater than min goal",Toast.LENGTH_LONG)
            }
            else
            {
                minGoal =binding.minET.text.toString().toInt()
                maxGoal =binding.maxET.text.toString().toInt()
                var intent = Intent(this,TimesheetEntriesListDisplay::class.java)
                intent.putExtra("minGoal",minGoal)
                intent.putExtra("maxGoal",maxGoal)
                startActivity(intent)
                finish()
            }
        }
    }
}