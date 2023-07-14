package com.momo.timeuptimetrackingapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

data class TimesheetEntry(
    val date: String,
    val startTime: String,
    val endTime: String,
    val description: String,
    val category: String,
    val imageUrl: String
)

class AddTimesheetEntryActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

    private lateinit var spinnerCategory: Spinner
    private lateinit var buttonAddPhoto: Button
    private lateinit var imageViewPhoto: ImageView
    private lateinit var editTextDescription: EditText
    private lateinit var listViewTimesheetEntries: ListView
    private val categoryList = mutableListOf<String>()
    private lateinit var selectedCategory: String
    private lateinit var database: FirebaseDatabase
    private lateinit var timesheetEntriesRef: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var capturedPhotoUri: Uri
    private lateinit var capturedPhotoBytes: ByteArray
    private lateinit var timesheetEntriesAdapter: ArrayAdapter<TimesheetEntry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_timesheet_entry)

        database = FirebaseDatabase.getInstance()
        timesheetEntriesRef = database.getReference("timesheet_entries")
        storageReference = FirebaseStorage.getInstance().reference

        val extras = intent.extras
        if (extras != null && extras.containsKey("categoryList")) {
            categoryList.addAll(extras.getStringArrayList("categoryList")!!)
        }

        spinnerCategory = findViewById(R.id.spinnerCategory)
        buttonAddPhoto = findViewById(R.id.buttonAddPhoto)
        imageViewPhoto = findViewById(R.id.imageViewPhoto)
        editTextDescription = findViewById(R.id.editTextDescription)
        listViewTimesheetEntries = findViewById(R.id.listViewTimesheetEntries)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        timesheetEntriesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        listViewTimesheetEntries.adapter = timesheetEntriesAdapter

        buttonAddPhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

        val buttonSave: Button = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            saveTimesheetEntry()
        }

        // Listen for changes in timesheet entries
        timesheetEntriesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val entries = mutableListOf<TimesheetEntry>()
                for (childSnapshot in snapshot.children) {
                    val entry = childSnapshot.getValue(TimesheetEntry::class.java)
                    entry?.let {
                        entries.add(entry)
                    }
                }
                updateTimesheetEntries(entries)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error if needed
            }
        })
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun saveTimesheetEntry() {
        val description = editTextDescription.text.toString()
        if (description.isEmpty()) {
            // Handle the case when description is empty
            return
        }

        selectedCategory = spinnerCategory.selectedItem.toString()

        val currentTime = System.currentTimeMillis()
        val imageRef = storageReference.child("timesheet_images/$currentTime.jpg")
        val uploadTask = imageRef.putBytes(capturedPhotoBytes)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val timesheetEntry = TimesheetEntry(
                    date = "", // Set the date, start time, end time, and description
                    startTime = "",
                    endTime = "",
                    description = description,
                    category = selectedCategory,
                    imageUrl = downloadUri.toString()
                )
                timesheetEntriesRef.push().setValue(timesheetEntry)

                // Clear the input fields
                editTextDescription.setText("")
                imageViewPhoto.setImageResource(0)
            } else {
                // Handle the case when image upload fails
            }
        }
    }

    private fun updateTimesheetEntries(entries: List<TimesheetEntry>) {
        timesheetEntriesAdapter.clear()
        timesheetEntriesAdapter.addAll(entries)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageViewPhoto.setImageBitmap(imageBitmap)
            capturedPhotoBytes = convertBitmapToByteArray(imageBitmap)
            saveTimesheetEntry()
        }
    }

    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }
}
