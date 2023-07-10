package com.momo.timeuptimetrackingapp
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Find the CardView elements by their IDs
        val profileCardView: CardView = findViewById(R.id.profileCard)
        val categoryCardView: CardView = findViewById(R.id.categoryCard)
        val timesheetCardView: CardView = findViewById(R.id.timesheetCard)
        val reportCardView: CardView = findViewById(R.id.reportCard)

        // Set onClick listeners for the CardView elements
        profileCardView.setOnClickListener {
            // Perform an action when Profile CardView is clicked
            // Replace the code below with your desired action
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            // Example: Start a new activity
            // startActivity(Intent(this, ProfileActivity::class.java))
        }

        categoryCardView.setOnClickListener {
            // Perform an action when Category CardView is clicked
            // Replace the code below with your desired action
            Toast.makeText(this, "Category clicked", Toast.LENGTH_SHORT).show()
            // Example: Start a new activity
             startActivity(Intent(this, AddCategoryActivity::class.java))
        }

        timesheetCardView.setOnClickListener {
            // Perform an action when Timesheet CardView is clicked
            // Replace the code below with your desired action
            Toast.makeText(this, "Timesheet clicked", Toast.LENGTH_SHORT).show()
            // Example: Start a new activity
             startActivity(Intent(this, AddTimesheetEntryActivity::class.java))
        }

        reportCardView.setOnClickListener {
            // Perform an action when Report CardView is clicked
            // Replace the code below with your desired action
            Toast.makeText(this, "Report clicked", Toast.LENGTH_SHORT).show()
            // Example: Start a new activity
             startActivity(Intent(this, ReportActivity::class.java))
        }
    }
}
