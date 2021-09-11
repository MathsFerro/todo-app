package com.example.todoapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.DetailTodoActivity
import com.example.todoapp.MainActivity
import com.example.todoapp.R

class TodoAdapter(): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = MainActivity.todos[position]

        val tvTitle = holder.view.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = holder.view.findViewById<TextView>(R.id.tvDescription)
        val ivDetail = holder.view.findViewById<ImageView>(R.id.ivDetail)
        val cbTodo = holder.view.findViewById<CheckBox>(R.id.cbTodo)

        tvTitle.text = todo.title
        tvDescription.text = todo.description
        cbTodo.isChecked = todo.isCompleted

        ivDetail.setOnClickListener {
            val intent = Intent(holder.view.context, DetailTodoActivity::class.java)
                .putExtra("todo", MainActivity.todos[position])

            holder.view.context.startActivity(intent)
        }

        cbTodo.setOnClickListener {
            todo.isCompleted = cbTodo.isChecked
            MainActivity.editTodo(todo, holder.view.context, null, null)
        }
    }

    override fun getItemCount(): Int = MainActivity.todos.size

}