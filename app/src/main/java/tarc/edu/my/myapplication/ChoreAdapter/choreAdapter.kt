package tarc.edu.my.myapplication.ChoreAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tarc.edu.my.myapplication.ChoreData.chores
import tarc.edu.my.myapplication.R


class choreAdapter(private val choresList: MutableList<chores>) : RecyclerView.Adapter<choreAdapter.MyViewHolder>(){

    private var mListener: onItemClickListener = object : onItemClickListener {
        override fun onItemClick(position: Int) {
            // Empty implementation
        }
    }

    interface onItemClickListener{
        fun  onItemClick(position : Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class MyViewHolder(itemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val price : TextView = itemView.findViewById(R.id.PriceTVSearch);
        val desc : TextView = itemView.findViewById(R.id.desTVSearch);
        val title : TextView = itemView.findViewById(R.id.TitleTVSearch);
        val startTime : TextView = itemView.findViewById(R.id.startTimeTV);
        val endTimeTv : TextView = itemView.findViewById(R.id.endTimeTV);

        init{

            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            //this is the search chore item (1 ROW ONLY)
            R.layout.fragment_search_chore_record,parent,false
        )
        return MyViewHolder(itemView,mListener)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = choresList[position]
        holder.title.text = currentitem.choreTitle
        holder.price.text = currentitem.chorePrice
        holder.desc.text = currentitem.choreDescription
        holder.startTime.text = currentitem.choreStart
        holder.endTimeTv.text = currentitem.choreEnd



    }

    override fun getItemCount(): Int {
        return choresList.size;
    }

    fun updateData(newChoresList: List<chores>) {
        choresList.clear()
        choresList.addAll(newChoresList)
        notifyDataSetChanged()
    }


}