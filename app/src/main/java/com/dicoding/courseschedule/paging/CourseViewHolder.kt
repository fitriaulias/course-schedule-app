package com.dicoding.courseschedule.paging

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.ui.detail.DetailActivity
import com.dicoding.courseschedule.ui.detail.DetailActivity.Companion.COURSE_ID
import com.dicoding.courseschedule.util.DayName.Companion.getByNumber
import org.w3c.dom.Text

class CourseViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private lateinit var course: Course
    private val timeString = itemView.context.resources.getString(R.string.time_format)

    val tvCourse: TextView = itemView.findViewById(R.id.tv_course)
    val tvTime: TextView = itemView.findViewById(R.id.tv_time)
    val tvLecturer: TextView = itemView.findViewById(R.id.tv_lecturer)


    //TODO 7 : Complete ViewHolder to show item
    fun bind(course: Course, clickListener: (Course) -> Unit) {
        this.course = course

        course.apply {
            val dayName = getByNumber(day)
            val timeFormat = String.format(timeString, dayName, startTime, endTime)

            tvCourse.text = course.courseName
            tvTime.text = timeFormat
            tvLecturer.text = course.lecturer

        }

        itemView.setOnClickListener {
            clickListener(course)
//            val intent = Intent(itemView.context, DetailActivity::class.java)
//            intent.putExtra(COURSE_ID, course.id)
//            itemView.context.startActivity(intent)
        }
    }

    fun getCourse(): Course = course
}