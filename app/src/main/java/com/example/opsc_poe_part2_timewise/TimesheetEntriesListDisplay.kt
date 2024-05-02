package com.example.opsc_poe_part2_timewise

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc_poe_part2_timewise.databinding.ActivityTimesheetEntriesListDisplayBinding
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimesheetEntriesListDisplay : AppCompatActivity() {
    private lateinit var binding: ActivityTimesheetEntriesListDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTimesheetEntriesListDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val layoutManager = LinearLayoutManager(this)
        binding.entriesRecyclerView.layoutManager = layoutManager
        val adapter = MyAdapter(TimesheetEntriesList.getEntriesList(),this, object:MyAdapter.OnItemClickListener{
            override fun onItemClick(item: TimesheetEntry) {
                var intent = Intent(applicationContext,TimesheetDetails::class.java)
                intent.putExtra("name", item.Name)
                intent.putExtra("category", item.Category.Name)
                intent.putExtra("description", item.Description)
                intent.putExtra("date", item.Date.time)
                intent.putExtra("timeSpent", item.TimeSpent.inWholeMilliseconds)
                intent.putExtra("image", item.Image.toString())
                startActivity(intent)
                finish()
            }
        } )
        binding.entriesRecyclerView.adapter=adapter

binding.newEntryBtn.setOnClickListener{
    var intent = Intent(this,NewTimesheetEntry::class.java)
    startActivity(intent)
    finish()
}
    }


    //ViewHolder class just to hold references to each view in the list_item layout
    class ItemViewHolder(itemView: View,private val listener: MyAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val image: ShapeableImageView = itemView.findViewById(R.id.titleImage)
        val name: TextView = itemView.findViewById(R.id.timesheetNameTV)
        var category: TextView = itemView.findViewById(R.id.categoryTV)
        val description: TextView = itemView.findViewById(R.id.timesheetDescTV)
        val date: TextView = itemView.findViewById(R.id.dateTV)
        val button : Button = itemView.findViewById(R.id.itemDisplayBtn)


        fun bind(item: TimesheetEntry) {
            // Bind item data to views here
            name.text = item.Name
            category.text = item.Category.Name
            description.text = item.Description
            date.text = item.Date.toString()
            button.setOnClickListener {
                listener.onItemClick(item)
            }

        }
    }

    /*var intent = Intent(parent.context,TimesheetDetails::class.java)
    intent.putExtra("name", item.Name)
    intent.putExtra("category", item.Category.Name)
    intent.putExtra("description", item.Description)
    intent.putExtra("date", item.Date)
    intent.putExtra("timeSpent", item.TimeSpent.inWholeMilliseconds)
    intent.putExtra("image", item.Image.toString())
    activity.startActivity(intent)
    activity.finish()*/
    //Adapter class to work with the viewss and assign values based on user data
    class MyAdapter(private val data: List<TimesheetEntry>,
                    private val activity: Activity,
                    private val listener: OnItemClickListener) :
        RecyclerView.Adapter<ItemViewHolder>() {
        interface OnItemClickListener {
            fun onItemClick(item: TimesheetEntry)
        }
        //creates viewholder objects to hold the views for manipulation
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.timesheet_list_item, parent, false)
            return ItemViewHolder(view, listener)
        }

        //used to bind values to the viewholder's properties
        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val currentItem = data[position]
            Picasso.get().load(currentItem.Image).into(holder.image)
            holder.name.text = currentItem.Name
            holder.category.text = currentItem.Category.Name
            holder.description.text = currentItem.Description
            holder.date.text = currentItem.Date.toString() // Set the date directly from the TimesheetEntry
            holder.bind(currentItem)
        }


        //returns the amount of entries in the data list
        override fun getItemCount(): Int {
            return data.size
        }

        //formatter for string to date conversion
        fun convertTextToDate(inputText: String): Date {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return dateFormat.parse(inputText) ?: Date()
        }
    }




}