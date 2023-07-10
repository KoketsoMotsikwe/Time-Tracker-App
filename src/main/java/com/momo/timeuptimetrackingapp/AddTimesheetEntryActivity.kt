package com.momo.timeuptimetrackingapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class AddTimesheetEntryActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

    private lateinit var spinnerCategory: Spinner
    private lateinit var buttonAddPhoto: Button
    private lateinit var imageViewPhoto: ImageView
    private val categoryList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_timesheet_entry)

        // Retrieve the category list from the intent extras
        val extras = intent.extras
        if (extras != null && extras.containsKey("categoryList")) {
            categoryList.addAll(extras.getStringArrayList("categoryList")!!)
        }

        spinnerCategory = findViewById(R.id.spinnerCategory)
        buttonAddPhoto = findViewById(R.id.buttonAddPhoto)
//        imageViewPhoto = findViewById(R.id.imageViewPhoto)

        // Populate the spinner with the available categories
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        buttonAddPhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Photo captured successfully, you can handle the captured photo here
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            // Display the captured photo in an ImageView if needed
            imageViewPhoto.setImageBitmap(imageBitmap)
        }
    }
}
