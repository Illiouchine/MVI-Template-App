package com.illiouchine.mviapplication.ui.first

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.illiouchine.mviapplication.R

class DataAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data : List<String> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_data, parent, false)
        return DataViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DataViewHolder).bind(data[position]){
            onClickListener(it)
        }
    }

    override fun getItemCount(): Int = data.count()

    private var onClickListener : (String) -> Unit = {}
    fun setOnClickListener(function: (String) -> Unit) {
        onClickListener = function
    }
}

class DataViewHolder(view: View): RecyclerView.ViewHolder(view){

    private val titleTextView : TextView = view.findViewById(R.id.vh_title)

    fun bind(data: String, onClick: (data: String)->Unit) {
        titleTextView.text = data
        itemView.setOnClickListener {
            onClick(data)
        }
    }

}