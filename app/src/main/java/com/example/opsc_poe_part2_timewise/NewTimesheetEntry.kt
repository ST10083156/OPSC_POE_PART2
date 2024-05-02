package com.example.opsc_poe_part2_timewise

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.opsc_poe_part2_timewise.databinding.ActivityNewTimesheetEntryBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.seconds

class NewTimesheetEntry : AppCompatActivity() {
    private var seconds = 0
    private var timerRunning = false
    private var lastRunningState = false
    private var startTime = 0L
    private var elapsedTime = 0L
    private lateinit var buttonTimer: Button
    private lateinit var timesheetEntry: TimesheetEntry
    private lateinit var currentUserID: String
    private lateinit var binding: ActivityNewTimesheetEntryBinding
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var spinner :Spinner
    private lateinit var selectedDate : Date


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewTimesheetEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val timesheetList = mutableListOf<TimesheetEntry>()

        val categoryNames = CategoriesList.getCategoriesList().map { it.Name }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner = binding.categorySpinner
        spinner.adapter = adapter

        val buttonAddPicture: Button = binding.buttonAddPicture
        buttonAddPicture.setOnClickListener {
            pictureAdd()
        }

        val calendarView = binding.dateCalendar
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            selectedDate = calendar.time
        }

        buttonTimer = binding.buttonTimer
        buttonTimer.setOnClickListener {
            if (timerRunning) {
                elapsedTime += System.currentTimeMillis() - startTime
                timerRunning = false
                buttonTimer.text = "Start Timer"
            } else {
                startTime = System.currentTimeMillis()
                timerRunning = true
                buttonTimer.text = "Stop Timer"
                runTimer()
            }
        }

        binding.doneBtn.setOnClickListener {
            if (binding.editTextName.text == null ||
                binding.categorySpinner.selectedItem == null ||
                binding.editTextDescription.text == null ||
                selectedDate == null ||
                binding.imageView.drawable == R.drawable.ic_launcher_foreground.toDrawable()
            ) {
                Toast.makeText(this, "Please fill all the relevant fields", Toast.LENGTH_LONG).show()
            } else {
                val totalTimePerCategory = mutableMapOf<String, Long>()

                for (entry in timesheetList) {
                    val categoryName = entry.Category.Name
                    val totalTime = totalTimePerCategory.getOrDefault(categoryName, 0L)
                    val timeSpentInSeconds = entry.TimeSpent.inWholeSeconds
                    totalTimePerCategory[categoryName] = totalTime + timeSpentInSeconds
                }

                val intent = Intent(this, TimesheetEntriesListDisplay::class.java).apply {
                    totalTimePerCategory.forEach { (category, total) ->
                        putExtra(category, total)
                    }
                }
                startActivity(intent)
                finish()
            }
        }
    }


    fun convertTextToDate(inputText: String): Date {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.parse(inputText) ?: Date()
    }

    private fun getImageUri(imageView: ImageView): Uri? {
        val drawable = imageView.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val uri = getImageUriFromBitmap(this, bitmap)
            return uri
        }
        return null
    }

    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }

    //this function is used to add a picture from the gallery to the imageview
    fun pictureAdd(){

        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(intent, PICK_IMAGE_REQUEST)

    }

    //if the picture adds successfully, this function sets the imageview to the picture selected
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            binding.imageView.setImageURI(selectedImageUri)
        }
    }
    private fun runTimer(){
        val handler = android.os.Handler()

        handler.postDelayed(
            {
                var hours = seconds/3600
                var minutes = (seconds%3600)/60

                val time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,seconds)
                binding.timerTV.text = time

                if (timerRunning) {
                    seconds++

                }


                runTimer() // Call runTimer again for recursive execution after 1 second delay
            },
            1000
        )

    }
}