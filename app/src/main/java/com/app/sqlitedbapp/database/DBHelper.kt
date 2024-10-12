package com.app.sqlitedbapp.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.app.sqlitedbapp.model.Task
import java.util.ArrayList

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "task_db"
        private val DATABASE_VERSION = 1
        private val TABLE_NAME = "tasks"
        private val ID = "id"
        private val TITLE = "title"
        private val DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $TITLE TEXT, $DESCRIPTION TEXT)"
            db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    //Get all the Data
    @SuppressLint("Range")
    fun getAllTask(): List<Task> {
        val taskList = ArrayList<Task>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val task = Task()
                    task.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    task.title = cursor.getString(cursor.getColumnIndex(TITLE))
                    task.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                    taskList.add(task)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return taskList
    }

    //Insert Data
    fun addTask(task: Task): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(TITLE, task.title)
        values.put(DESCRIPTION, task.description)
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    //Select data from particular ID
    @SuppressLint("Range")
    fun getTask(id: Int): Task {
        val task = Task()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $id"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        task.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        task.title = cursor.getString(cursor.getColumnIndex(TITLE))
        task.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
        cursor.close()
        return task
    }

    //Delete Data
    fun deleteTask(id: Int): Boolean {
        val db = writableDatabase
        val success = db.delete(TABLE_NAME, "$ID = $id", null)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    //Update Data
    fun updateTask(task: Task): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(TITLE, task.title)
        values.put(DESCRIPTION, task.description)
        val success = db.update(TABLE_NAME, values, "$ID = ${task.id}", null)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }
}