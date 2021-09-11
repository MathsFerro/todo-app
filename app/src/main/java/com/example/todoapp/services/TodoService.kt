package com.example.todoapp.services

import com.example.todoapp.models.Todo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TodoService {

    @GET("todos")
    fun findAll(): Call<List<Todo>>

    @POST("todos")
    fun addTodo(@Body todo: Todo): Call<Todo>

    @PUT("todos/{id}")
    fun editTodo(@Path("id") id: Int, @Body todo: Todo): Call<Todo>

    @DELETE("todos/{id}")
    fun deleteTodo(@Path("id") id: Int): Call<Todo>

}

class TodoConnection {
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://61369f9c8700c50017ef55da.mockapi.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service = retrofit.create(TodoService::class.java)
}