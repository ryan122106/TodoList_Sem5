package com.example.todolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.models.TodoDetails

class TodoRecycleViewAdapter(
    private var items: List<TodoDetails>,
    private val onItemClick: (TodoDetails) -> Unit,
    private val onDeleteClick: (TodoDetails) -> Unit,
    private val onEditClick: (TodoDetails) -> Unit
) : RecyclerView.Adapter<TodoRecycleViewAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.txtTitle)
        val detail: TextView = view.findViewById(R.id.txtDetail)

        // 🔥 add buttons in XML
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.title
        holder.detail.text = item.details

        // 🔥 click to open details
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<TodoDetails>) {
        items = newList
        notifyDataSetChanged()
    }
}