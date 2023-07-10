package com.momo.timeuptimetrackingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddCategoryActivity : AppCompatActivity() {
    private lateinit var editTextCategoryName: EditText
    private lateinit var buttonCreateCategory: Button
    private val categoryList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        editTextCategoryName = findViewById(R.id.editTextCategory)
        buttonCreateCategory = findViewById(R.id.buttonSave)

        buttonCreateCategory.setOnClickListener {
            val categoryName = editTextCategoryName.text.toString()
            // Perform any validation or processing on the category name

            // Save the category to the list
            saveCategory(categoryName)

            // Start the AddTimesheetEntryActivity and pass the category list as an extra
            val intent = Intent(this, AddTimesheetEntryActivity::class.java)
            intent.putStringArrayListExtra("categoryList", ArrayList(categoryList))
            startActivity(intent)

            // Optionally, you can go back to the previous activity or perform any other actions
            finish()
        }
    }

    private fun saveCategory(categoryName: String) {
        // Add the category to the list
        categoryList.add(categoryName)

        // Print the list of categories (for demonstration purposes)
        println("Categories: $categoryList")
    }
}
