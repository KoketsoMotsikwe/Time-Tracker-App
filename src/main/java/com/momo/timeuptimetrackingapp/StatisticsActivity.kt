package com.momo.timeuptimetrackingapp

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import lecho.lib.hellocharts.model.Axis
import lecho.lib.hellocharts.model.AxisValue
import lecho.lib.hellocharts.model.Line
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.PointValue
import lecho.lib.hellocharts.util.ChartUtils
import lecho.lib.hellocharts.view.LineChartView

class StatisticsActivity : AppCompatActivity() {
    private lateinit var datePickerStartDate: DatePicker
    private lateinit var datePickerEndDate: DatePicker
    private lateinit var buttonShowStatistics: Button
    private lateinit var textViewStatistics: TextView
    private lateinit var lineChartView: LineChartView
    private lateinit var minMaxGoalsTextView: TextView
    private lateinit var chartLayout: LinearLayout
    private lateinit var timesheetEntriesRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        // Initialize database reference
        val database = FirebaseDatabase.getInstance()
        timesheetEntriesRef = database.getReference("timesheet_entries")

        datePickerStartDate = findViewById(R.id.datePickerStartDate)
        datePickerEndDate = findViewById(R.id.datePickerEndDate)
        buttonShowStatistics = findViewById(R.id.buttonShowStatistics)
        textViewStatistics = findViewById(R.id.textViewStatistics)
        lineChartView = findViewById(R.id.chartView)
        minMaxGoalsTextView = findViewById(R.id.minMaxGoalsTextView)
        chartLayout = findViewById(R.id.chartLayout)

        buttonShowStatistics.setOnClickListener {
            val startDate = getSelectedDate(datePickerStartDate)
            val endDate = getSelectedDate(datePickerEndDate)

            if (startDate != null && endDate != null) {
                // Retrieve timesheet entries within the selected period
                retrieveTimesheetEntries(startDate, endDate)
            }
        }
    }

    private fun getSelectedDate(datePicker: DatePicker): String? {
        val day = datePicker.dayOfMonth
        val month = datePicker.month + 1
        val year = datePicker.year
        return "$day/$month/$year"
    }

    private fun retrieveTimesheetEntries(startDate: String, endDate: String) {
        timesheetEntriesRef.orderByChild("date").startAt(startDate).endAt(endDate)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val statistics = calculateStatistics(snapshot)
                    displayStatistics(statistics)
                    displayGraph(statistics)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database retrieval error
                }
            })
    }

    private fun calculateStatistics(snapshot: DataSnapshot): Map<String, Double> {
        val statistics = mutableMapOf<String, Double>()

        for (entrySnapshot in snapshot.children) {
            val entry = entrySnapshot.getValue(TimesheetEntry::class.java)
            entry?.let {
                val category = entry.category
                val hours = calculateHours(entry.startTime, entry.endTime)
                if (statistics.containsKey(category)) {
                    val totalHours = statistics[category] ?: 0.0
                    statistics[category] = totalHours + hours
                } else {
                    statistics[category] = hours
                }
            }
        }

        return statistics
    }

    private fun calculateHours(startTime: String, endTime: String): Double {
        // Calculate the hours based on the startTime and endTime values
        // Return the calculated hours as a double value

        val startParts = startTime.split(":")
        val endParts = endTime.split(":")

        val startHour = startParts[0].toDouble()
        val startMinute = startParts[1].toDouble()

        val endHour = endParts[0].toDouble()
        val endMinute = endParts[1].toDouble()

        val totalMinutes = (endHour - startHour) * 60 + (endMinute - startMinute)
        val hours = totalMinutes / 60

        return hours
    }


    private fun displayStatistics(statistics: Map<String, Double>) {
        val stringBuilder = StringBuilder()

        for ((category, hours) in statistics) {
            stringBuilder.append("$category: $hours hours\n")
        }

        textViewStatistics.text = stringBuilder.toString()
    }

    private fun displayGraph(statistics: Map<String, Double>) {
        val lines = mutableListOf<Line>()
        val values = mutableListOf<PointValue>()
        val axisValues = mutableListOf<AxisValue>()

        var index = 0
        for ((category, hours) in statistics) {
            values.add(PointValue(index.toFloat(), hours.toFloat()))
            axisValues.add(AxisValue(index.toFloat()).setLabel(category))
            index++
        }

        val line = Line(values)
        line.color = ChartUtils.COLOR_GREEN
        line.setHasLines(true) // Show lines
        lines.add(line)

        val data = LineChartData(lines)
        val axisX = Axis()
        val axisY = Axis().apply {
            name = "Hours"
        }

        axisX.values = axisValues
        data.axisXBottom = axisX
        data.axisYLeft = axisY

        lineChartView.lineChartData = data
        lineChartView.invalidate()
    }
}
