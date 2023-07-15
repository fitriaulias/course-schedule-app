package com.dicoding.courseschedule.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import com.dicoding.courseschedule.util.TimePickerFragment

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var addCourseViewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        val factory = AddCourseViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val courseName = findViewById<EditText>(R.id.add_course_name).text.toString()
                val dayName = findViewById<Spinner>(R.id.add_spinner_days).selectedItem?.toString()
                val dayInt: Int
                when(dayName) {
                    "Monday" -> dayInt = 1
                    "Tuesday" -> dayInt = 2
                    "Wednesday" -> dayInt = 3
                    "Thursday" -> dayInt = 4
                    "Friday" -> dayInt = 5
                    "Saturday" -> dayInt = 6
                    "Sunday" -> dayInt = 0
                    else -> dayInt = 0
                }
                val startTime = findViewById<TextView>(R.id.add_tv_start_time).text.toString()
                val endTime = findViewById<TextView>(R.id.add_tv_end_time).text.toString()
                val lecturer = findViewById<TextView>(R.id.add_lecturer).text.toString()
                val note = findViewById<TextView>(R.id.add_note).text.toString()

                addCourseViewModel.insertCourse(courseName, dayInt, startTime, endTime, lecturer, note)

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showTimePicker(view: View) {
        val dialogFragment = TimePickerFragment()
        if (view.id == R.id.ib_start_time) {
            dialogFragment.show(supportFragmentManager, "timePickerStart")
        } else if (view.id == R.id.ib_end_time) {
            dialogFragment.show(supportFragmentManager, "timePickerEnd")
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, min: Int) {
        val hourHandler = String.format("%02d", hour)
        val minuteHandler = String.format("%02d", min)

        val currentTime = "$hourHandler:$minuteHandler"

        if(tag.equals("timePickerStart")) {
            findViewById<TextView>(R.id.add_tv_start_time).text = currentTime
        } else if (tag.equals("timePickerEnd")) {
            findViewById<TextView>(R.id.add_tv_end_time).text = currentTime
        }
    }

}