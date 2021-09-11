package com.example.todoapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.todoapp.databinding.ActivityDetailTodoBinding
import com.example.todoapp.models.Todo
import com.example.todoapp.services.TodoConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTodoBinding
    private var editable: Boolean = false

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.elevation = 0.0f
        binding = ActivityDetailTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val todo = intent.extras!!.get("todo") as Todo

        val etTitle = binding.etTitle
        val etDescription = binding.etDescription
        val fbEdit = binding.fbEditTodo
        val fbDelete = binding.fbDeleteTodo

        etTitle.setText(todo.title)
        etDescription.setText(todo.description)
        fbDelete.visibility = View.INVISIBLE

        handleClickableText(etTitle, etDescription, true)

        fbEdit.setOnClickListener {
            handleClickableText(etTitle, etDescription)

            if(editable) {
                etTitle.setBackgroundResource(R.drawable.no_edit_bg)
                etDescription.setBackgroundResource(R.drawable.no_edit_bg)

                val title = etTitle.text
                val description = etDescription.text

                MainActivity.editTodo(
                    todo = Todo(
                        id = todo.id,
                        title = title.toString(),
                        description = description.toString(),
                        isCompleted = false
                    ),
                    context = baseContext,
                    messageSucess = "TODO Editado com Sucesso",
                    messageFail = "Falha ao editar esse TODO"
                )

                editable = false
                fbDelete.visibility = View.INVISIBLE
                fbEdit.setImageDrawable(resources.getDrawable(R.drawable.ic_edit))
            } else {
                etTitle.setBackgroundResource(R.drawable.edit_bg)
                etDescription.setBackgroundResource(R.drawable.edit_bg)

                editable = true
                fbDelete.visibility = View.VISIBLE
                fbEdit.setImageDrawable(resources.getDrawable(R.drawable.ic_done_edit))
            }
        }

        fbDelete.setOnClickListener {
            MainActivity.deleteTodo(
                id = todo.id!!.toInt(),
                context = baseContext,
                messageSucess = "TODO deletado com sucesso",
                messageFail = "Falha ao deletar TODO"
            )

            startActivity(Intent(baseContext, MainActivity::class.java))
        }
    }

    private fun handleClickableText(etTitle: EditText, etDescription: EditText, firstLoad: Boolean = false) {
        var isClickable = !editable
        if(firstLoad)
            isClickable = false

        etTitle.isFocusable = isClickable
        etTitle.isFocusableInTouchMode = isClickable
        etTitle.isClickable = isClickable

        etDescription.isFocusable = isClickable
        etDescription.isFocusableInTouchMode = isClickable
        etDescription.isClickable = isClickable
    }

}