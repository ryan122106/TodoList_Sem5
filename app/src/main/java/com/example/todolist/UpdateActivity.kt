package com.example.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todolist.models.ToDoStatus
import com.example.todolist.models.TodoDetails
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_xml)

        val id = intent.getIntExtra("id", -1)
        val title = intent.getStringExtra("title")
        val meaning = intent.getStringExtra("meaning")
        val synonym = intent.getStringExtra("synonym")
        val details = intent.getStringExtra("details")

        if (id == -1) {
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // ✅ FIXED IDs
        val etTitle = findViewById<EditText>(R.id.inputTitle)
        val etMeaning = findViewById<EditText>(R.id.inputMeaning)
        val etSynonym = findViewById<EditText>(R.id.inputSynonyms)
        val etDetails = findViewById<EditText>(R.id.inputDetails)
        val btnSave = findViewById<Button>(R.id.btnSave)

        // set old data
        etTitle.setText(title)
        etMeaning.setText(meaning)
        etSynonym.setText(synonym)
        etDetails.setText(details)

        btnSave.setOnClickListener {

            val updatedItem = TodoDetails(
                id = id,
                title = etTitle.text.toString(),
                meaning = etMeaning.text.toString(),
                synonyms = etSynonym.text.toString(),
                details = etDetails.text.toString(),
                status = ToDoStatus.NEW
            )

            lifecycleScope.launch {
                MainActivity.todoDao.update(updatedItem)

                Toast.makeText(this@UpdateActivity, "Updated Successfully", Toast.LENGTH_SHORT).show()

                setResult(RESULT_OK)
                finish()
            }
        }
    }
}