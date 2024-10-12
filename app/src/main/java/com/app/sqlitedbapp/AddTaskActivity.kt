package com.app.sqlitedbapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.sqlitedbapp.database.DBHelper
import com.app.sqlitedbapp.databinding.ActivityAddTaskBinding
import com.app.sqlitedbapp.model.Task

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    var dbHelper : DBHelper?= null
    var isEditMode : Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        if (intent != null && intent.getStringExtra("mode") == "edit") {
            //Update Data
            isEditMode = true
            binding.btnSave.text = "UPDATE"
            binding.btnDelete.visibility = View.VISIBLE

            val task : Task = dbHelper!!.getTask(intent.getIntExtra("id",0))
            binding.edtTitle.setText(task.title)
            binding.edtDescription.setText(task.description)
        } else {
            //Insert New Data
            isEditMode = false
            binding.btnSave.text = "SAVE"
            binding.btnDelete.visibility = View.GONE
        }

        binding.btnSave.setOnClickListener {
            val success : Boolean
            val task : Task = Task()
            if (isEditMode) {
                //Update Data
                task.id = intent.getIntExtra("id",0)
                task.title = binding.edtTitle.text.toString()
                task.description = binding.edtDescription.text.toString()

                success = dbHelper!!.updateTask(task)
            } else {
                //Insert New Data
                task.title = binding.edtTitle.text.toString()
                task.description = binding.edtDescription.text.toString()
                success = dbHelper!!.addTask(task)
            }
            if (success) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Something Went Wrong!!!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(this).setTitle("Alert!!!").setMessage("Click YES If You Want To Delete This Task")
                .setPositiveButton("YES", { dialog, i ->
                    val success = dbHelper!!.deleteTask(intent.getIntExtra("id", 0))
                    if(success) {
                        finish()
                    }
                }).setNegativeButton("NO", { dialog, i ->
                    dialog.dismiss()
                })
            dialog.show()
        }
    }
}