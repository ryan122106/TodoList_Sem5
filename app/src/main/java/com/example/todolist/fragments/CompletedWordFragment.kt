package com.example.todolist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.MainActivity
import com.example.todolist.R
import com.example.todolist.WordDetailsActivity
import com.example.todolist.adapters.TodoRecycleViewAdapter
import com.example.todolist.models.TodoDetails
import kotlinx.coroutines.launch
import android.text.TextWatcher
import android.text.Editable
import com.example.todolist.models.ToDoStatus

class CompletedWordFragment : Fragment(R.layout.completed_word_fragment) {

    private lateinit var adapter: TodoRecycleViewAdapter

    private var fullList: List<TodoDetails> = listOf()
    private var filteredList: List<TodoDetails> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val emptyView = view.findViewById<View>(R.id.emptyView)
        val sortButton = view.findViewById<ImageButton>(R.id.imageButton3)
        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)

        adapter = TodoRecycleViewAdapter(
            items = listOf(),

            onItemClick = { item ->
                val intent = Intent(requireContext(), WordDetailsActivity::class.java)
                intent.putExtra("id", item.id)
                intent.putExtra("title", item.title)
                intent.putExtra("meaning", item.meaning)
                intent.putExtra("synonym", item.synonyms)
                intent.putExtra("details", item.details)
                startActivity(intent)
            },

            onDeleteClick = { item ->
                lifecycleScope.launch {
                    MainActivity.todoDao.delete(item)
                }
            },

            onEditClick = { item ->
                val intent = Intent(requireContext(), WordDetailsActivity::class.java)
                intent.putExtra("id", item.id)
                intent.putExtra("title", item.title)
                intent.putExtra("meaning", item.meaning)
                intent.putExtra("synonym", item.synonyms)
                intent.putExtra("details", item.details)
                startActivity(intent)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // 🔥 OBSERVE DATABASE
        MainActivity.todoDao.getAll().observe(viewLifecycleOwner) { list ->

            // 🔥 filter ONLY completed items
            val doneList = list.filter {it.status == ToDoStatus.DONE }
            // ⚠️ change if you use enum (see note below)

            fullList = doneList
            filteredList = doneList

            updateUI(recyclerView, emptyView, doneList)
        }

        // 🔍 SEARCH
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase()

                val filtered = fullList.filter {
                    it.title.lowercase().contains(query)
                }

                filteredList = filtered
                updateUI(recyclerView, emptyView, filtered)
            }
        })

        // 🔥 SORT
        sortButton.setOnClickListener {

            val dialogView = layoutInflater.inflate(R.layout.dialog_sort, null)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()

            val rbAsc = dialogView.findViewById<RadioButton>(R.id.rbAsc)
            val rbDesc = dialogView.findViewById<RadioButton>(R.id.rbDesc)
            val btnApply = dialogView.findViewById<Button>(R.id.btnApply)

            btnApply.setOnClickListener {

                val sortedList = if (rbAsc.isChecked) {
                    filteredList.sortedBy { it.title }
                } else {
                    filteredList.sortedByDescending { it.title }
                }

                filteredList = sortedList
                updateUI(recyclerView, emptyView, sortedList)

                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun updateUI(
        recyclerView: RecyclerView,
        emptyView: View,
        list: List<TodoDetails>
    ) {
        adapter.updateList(list)

        if (list.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
    }
}