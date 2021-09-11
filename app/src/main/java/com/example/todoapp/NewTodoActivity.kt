package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todoapp.databinding.ActivityNewTodoBinding
import com.example.todoapp.models.Todo
import com.example.todoapp.services.TodoConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.elevation = 0.0f
        binding = ActivityNewTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btAddTodo.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description =  binding.etDescription.text.toString()

            if(title.isBlank() || description.isBlank()) {
                Toast.makeText(
                    this@NewTodoActivity,
                    "Preencha todos os campos",
                    Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            add(Todo(
                id = null,
                title = binding.etTitle.text.toString(),
                description = binding.etDescription.text.toString(),
                isCompleted = false
            ))
        }
    }

    private fun add(todo: Todo) {
        TodoConnection().service.addTodo(todo)
            .enqueue(object: Callback<Todo>{
                override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                    val msg = if(response.isSuccessful) "Todo adicionado com sucesso" else "Erro ao adicionar Todo"
                    Toast.makeText(this@NewTodoActivity, msg, Toast.LENGTH_SHORT).show()

                }
                override fun onFailure(call: Call<Todo>, t: Throwable) {
                    Toast.makeText(this@NewTodoActivity, "Erro ao adicionar Todo", Toast.LENGTH_SHORT).show()
                }
            })

        binding.etTitle.text = null
        binding.etDescription.text = null

        startActivity(Intent(baseContext, MainActivity::class.java))
    }

}