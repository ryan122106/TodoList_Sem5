package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todolist.models.ToDoStatus
import com.example.todolist.models.TodoDetails
import kotlinx.coroutines.launch

class WordDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.words_details)

        val id = intent.getIntExtra("id", -1)
        val title = intent.getStringExtra("title")
        val meaning = intent.getStringExtra("meaning")
        val synonym = intent.getStringExtra("synonym")
        val details = intent.getStringExtra("details")

        val tvTitle = findViewById<TextView>(R.id.txtTitle2)
        val tvMeaning = findViewById<TextView>(R.id.txtMeaning)
        val tvSynonym = findViewById<TextView>(R.id.txtSynonym)
        val tvDetails = findViewById<TextView>(R.id.txtDetails)

        val btnDone = findViewById<Button>(R.id.btnDone)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        // show data
        tvTitle.text = title ?: ""
        tvMeaning.text = meaning ?: ""
        tvSynonym.text = synonym ?: ""
        tvDetails.text = details ?: ""

        // DONE
        btnDone.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Mark as Done")
                .setMessage("Are you sure you want to mark this item as completed?")
                .setPositiveButton("Yes") { _, _ ->

                    val updatedItem = TodoDetails(
                        id = id,
                        title = title ?: "",
                        meaning = meaning ?: "",
                        synonyms = synonym ?: "",
                        details = details ?: "",
                        status = ToDoStatus.DONE   // 🔥 move to completed
                    )

                    lifecycleScope.launch {
                        MainActivity.todoDao.update(updatedItem)
                        finish()
                    }
                }
                .setNegativeButton("No", null)
                .show()
        }

        // DELETE
        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { _, _ ->

                    lifecycleScope.launch {
                        MainActivity.todoDao.delete(
                            TodoDetails(
                                id,
                                title ?: "",
                                meaning ?: "",
                                synonym ?: "",
                                details ?: "",
                                ToDoStatus.NEW
                            )
                        )
                        finish()
                    }
                }
                .setNegativeButton("No", null)
                .show()
        }

        // UPDATE → go to update page
        btnUpdate.setOnClickListener {
            val intent = Intent(this, UpdateActivity::class.java)

            intent.putExtra("id", id)
            intent.putExtra("title", title)
            intent.putExtra("meaning", meaning)
            intent.putExtra("synonym", synonym)
            intent.putExtra("details", details)

            startActivity(intent)
        }
    }
}