package com.example.todolist.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.todolist.MainActivity
import com.example.todolist.R
import com.example.todolist.models.TodoDetails
import com.example.todolist.models.ToDoStatus
import kotlinx.coroutines.launch

class AddTodoFragment : Fragment(R.layout.add_todo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTitle = view.findViewById<EditText>(R.id.inputTitle)
        val etMeaning = view.findViewById<EditText>(R.id.inputMeaning)
        val etSynonym = view.findViewById<EditText>(R.id.inputSynonyms)
        val etDetails = view.findViewById<EditText>(R.id.inputDetails
        )
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener {

            val title = etTitle.text.toString()
            val meaning = etMeaning.text.toString()
            val synonym = etSynonym.text.toString()
            val details = etDetails.text.toString()

            if (title.isEmpty()) return@setOnClickListener

            lifecycleScope.launch {

                MainActivity.todoDao.insert(
                    TodoDetails(
                        title = title,
                        meaning = meaning,
                        synonyms = synonym,
                        details = details,
                        status = ToDoStatus.NEW
                    )
                )
            }

            // clear fields
            etTitle.text.clear()
            etMeaning.text.clear()
            etSynonym.text.clear()
            etDetails.text.clear()

            // 🔥 GO BACK TO LIST PAGE
            parentFragmentManager.popBackStack()
        }
    }
}