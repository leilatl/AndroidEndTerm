package com.example.endterm1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.comment_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_item.view.titleTextView
import kotlinx.android.synthetic.main.list_item.view.toDoItemLayout


class CommentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var taskList = emptyList<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return ContactViewHolder(v)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ContactViewHolder
        val currentItem = taskList[position]
        holder.itemView.titleTextView.text = currentItem.email
        holder.itemView.statusTextView.text = currentItem.name


    }
    fun setData(task: List<Comment>){

        this.taskList = task
        notifyDataSetChanged()
    }
    inner class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    }
}