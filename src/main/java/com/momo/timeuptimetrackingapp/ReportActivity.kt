package com.momo.timeuptimetrackingapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import lecho.lib.hellocharts.model.Axis
import lecho.lib.hellocharts.model.AxisValue
import lecho.lib.hellocharts.model.Line
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.PointValue
import lecho.lib.hellocharts.util.ChartUtils
import lecho.lib.hellocharts.view.LineChartView

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val lineChartView = findViewById<LineChartView>(R.id.chartView)

        // Create line chart data
        val lines = mutableListOf<Line>()

        // Create line data series
        val values = mutableListOf<PointValue>()
        values.add(PointValue(0f, 2f))
        values.add(PointValue(1f, 4f))
        values.add(PointValue(2f, 6f))
        values.add(PointValue(3f, 8f))
        values.add(PointValue(4f, 10f))


        val line = Line(values)
        line.color = ChartUtils.COLOR_GREEN
        line.setHasLines(true) // Show lines

        lines.add(line)

        // Set line chart data
        val data = LineChartData(lines)
        lineChartView.lineChartData = data

        // Set axis labels
        val axisX = Axis()
        val axisY = Axis().apply {
            name = "Value"
        }

        val axisValues = mutableListOf<AxisValue>()
        axisValues.add(AxisValue(0f).setLabel("0"))
        axisValues.add(AxisValue(1f).setLabel("1"))
        axisValues.add(AxisValue(2f).setLabel("2"))
        axisValues.add(AxisValue(3f).setLabel("3"))
        axisValues.add(AxisValue(4f).setLabel("4"))

        axisX.values = axisValues

        data.axisXBottom = axisX
        data.axisYLeft = axisY

        // Customize other chart properties as needed

        // Update chart view
        lineChartView.invalidate()
    }
}
