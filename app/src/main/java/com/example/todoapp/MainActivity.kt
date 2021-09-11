package com.example.todoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todoapp.adapter.TodoAdapter
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.models.Todo
import com.example.todoapp.services.TodoConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    companion object {
        var todos: List<Todo> = listOf()

        fun editTodo(todo: Todo, context: Context, messageSucess: String? = null, messageFail: String? = null) {
            TodoConnection().service.editTodo(todo.id!!.toInt(), todo)
                .enqueue(object: Callback<Todo>{
                    override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                        if(messageSucess!=null) {
                            if(response.isSuccessful)
                                Toast.makeText(context, messageSucess, Toast.LENGTH_SHORT).show()
                            else
                                Toast.makeText(context, messageFail, Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<Todo>, t: Throwable) {
                        if(messageSucess!=null) {
                            Toast.makeText(context, messageFail, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }

        fun deleteTodo(id: Int, context: Context, messageSucess: String? = null, messageFail: String? = null) {
            TodoConnection().service.deleteTodo(id).enqueue(object: Callback<Todo>{
                override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                    if(messageSucess!=null) {
                        if(response.isSuccessful)
                            Toast.makeText(context, messageSucess, Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(context, messageFail, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Todo>, t: Throwable) {
                    if(messageSucess!=null) {
                        Toast.makeText(context, messageFail, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        findAll()
        setContentView(binding.root)

        binding.fbAddTodo.setOnClickListener {
            val intent = Intent(this, NewTodoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun findAll() {
        TodoConnection().service.findAll().enqueue(object: Callback<List<Todo>>{
            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                if (!response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Falha ao buscar todos os TODO's", Toast.LENGTH_SHORT).show()
                    return
                }

                response.body()?.let { todo ->
                    todos = todo
                    val todoAdapter = TodoAdapter()
                    binding.rvTodos.adapter = todoAdapter
                } ?: run {
                    Toast.makeText(this@MainActivity, "Falha ao buscar todos os TODO's", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha ao buscar todos os TODO's", Toast.LENGTH_SHORT).show()
            }

        })
    }
}