package com.example.test
    
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class WorkoutAdapter:RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>()
{
    private val vIEWTYPEEXERCISE = 1
    private val vIEWTYPECATEGORY = 2
    private var workoutList: List<WorkoutDataClass> = emptyList() // Initialize movies with an empty list

    
    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val workoutNameTextView: TextView = itemView.findViewById(R.id.workoutNameTextView)
        private val categoryNameTextView: TextView = itemView.findViewById(R.id.workoutCategoryTextView)
    
        fun bind(workout: WorkoutDataClass) {
            workoutNameTextView.text = workout.name
            categoryNameTextView.visibility = View.GONE
            }
    
        fun bind(category: CategoryDataClass) {
            categoryNameTextView.text = category.category
            workoutNameTextView.visibility = View.GONE
            }
        }
    
        fun updateWorkout(newExercise: List<WorkoutDataClass>) {
            workoutList = newExercise

    
        }
    override fun getItemViewType(position: Int): Int {
        return if (workoutList[position] is WorkoutDataClass) {
            vIEWTYPEEXERCISE
        } else {
            vIEWTYPECATEGORY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
            return if (viewType == vIEWTYPEEXERCISE) {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
                WorkoutViewHolder(view)
            } else {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
                WorkoutViewHolder(view)
            }
        }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
            return workoutList.size
        }
    
    }
    
