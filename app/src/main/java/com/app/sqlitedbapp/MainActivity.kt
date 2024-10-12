package com.app.sqlitedbapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.sqlitedbapp.adapter.TaskListAdapter
import com.app.sqlitedbapp.database.DBHelper
import com.app.sqlitedbapp.databinding.ActivityMainBinding
import com.app.sqlitedbapp.model.Task

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var taskListAdapter: TaskListAdapter ?= null
    var dbHelper : DBHelper ?= null
    var taskList : List<Task> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        fetchList()

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchList() {
        taskList = dbHelper!!.getAllTask()
        taskListAdapter = TaskListAdapter(this, taskList as ArrayList<Task>)
        binding.recyclerViewList.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewList.adapter = taskListAdapter
        taskListAdapter?.notifyDataSetChanged()
    }
}