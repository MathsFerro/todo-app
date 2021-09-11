package com.example.todoapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Todo(
    @SerializedName("id") val id: Long?,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("is_completed") var isCompleted: Boolean
) : Serializable