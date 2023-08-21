package tarc.edu.my.myapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import tarc.edu.my.myapplication.data.Review

class ReviewAdapter (private val recordClickListener: RecordClickListener): RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    private var reviewList = emptyList<Review>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cardViewTitle: TextView = view.findViewById(R.id.CardViewTitle)
        val des: TextView = view.findViewById(R.id.des)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.record, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardViewTitle.text = reviewList[position].title
        holder.des.text = reviewList[position].desc
        holder.itemView.setOnClickListener {
            recordClickListener.onRecordClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    internal fun setData(review: List<Review>) {
        this.reviewList = review
        notifyDataSetChanged()
    }

    interface RecordClickListener {
        fun onRecordClickListener(index: Int)
    }
}