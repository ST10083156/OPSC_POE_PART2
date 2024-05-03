import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc_poe_part2_timewise.CategoryTime
import com.example.opsc_poe_part2_timewise.R

class CategoryTimeAdapter(private val categoryTimeList: List<CategoryTime>) :
    RecyclerView.Adapter<CategoryTimeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryNameTextView: TextView = itemView.findViewById(R.id.categoryNameTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.categoryTotalTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_category_total_hours, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryTime = categoryTimeList[position]
        holder.categoryNameTextView.text = categoryTime.categoryName
        holder.timeTextView.text = categoryTime.timeInSeconds.toString()
    }

    override fun getItemCount(): Int {
        return categoryTimeList.size
    }
}
