package com.example.opsc_poe_part2_timewise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc_poe_part2_timewise.databinding.ActivityCategoriesPageBinding

class CategoriesPage : AppCompatActivity() {
    private lateinit var binding : ActivityCategoriesPageBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       binding = ActivityCategoriesPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var categoriesList = mutableListOf<Category>()
        adapter = MyAdapter(categoriesList)
        recyclerView=binding.categoriesRV
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.addBtn.setOnClickListener{
            if(binding.categoryET.text!=null){
                var category = Category(binding.categoryET.text.toString())
                categoriesList.add(category)
                adapter.notifyDataSetChanged()
                binding.categoryET.text.clear()

            }
            else {
            Toast.makeText(this,"Please fill in the category name",Toast.LENGTH_LONG)
            }
            }
        binding.doneBtn.setOnClickListener{
 var doneCategoriesList = CategoriesList.init(categoriesList)
var intent = Intent(this,NewTimesheetEntry::class.java)
            startActivity(intent)
            finish()
        }

    }
    }
class MyAdapter(private val dataList: MutableList<Category>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView: TextView = itemView.findViewById(R.id.itemTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTextView.text = dataList[position].Name
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

