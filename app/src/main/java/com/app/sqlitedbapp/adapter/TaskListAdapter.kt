package com.app.sqlitedbapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.sqlitedbapp.AddTaskActivity
import com.app.sqlitedbapp.databinding.TaskItemBinding
import com.app.sqlitedbapp.model.Task

class TaskListAdapter(val context: Context, val taskList: ArrayList<Task>): Adapter<TaskListAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: TaskItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.title.text = task.title
        holder.binding.description.text = task.description
        holder.binding.btnEdit.setOnClickListener {
            val intent = Intent(context, AddTaskActivity::class.java)
            intent.putExtra("mode", "edit")
            intent.putExtra("id", task.id)
            context.startActivity(intent)
        }
    }
}