package com.example.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorkoutAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = WorkoutAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        // Fetch data from API and update adapters
        fetchWorkoutData()
    }

    private fun fetchWorkoutData() {
        val client = AsyncHttpClient()

        client.get("https://wger.de/api/v2/exerciseinfo/En", object :
            TextHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, response: String) {
                try {
                    val jsonResponse = JSONObject(response)
                    val exercises = parseWorkoutResponse(jsonResponse)
                    adapter.updateWorkout(exercises)

                } catch (e: JSONException) {
                    // Handle JSON parsing exception
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }
        })
    }



    private fun parseWorkoutResponse(response: JSONObject): List<WorkoutDataClass> {
        val workoutList = mutableListOf<WorkoutDataClass>()
        val resultsArray = response.optJSONArray("results")

        resultsArray?.let {
            for (i in 0 until it.length()) {
                val exerciseObject = it.getJSONObject(i)
                val name = exerciseObject.optString("name")
                val exercise = WorkoutDataClass(name)
                workoutList.add(exercise)
            }
        }

        return workoutList
    }

    private fun parseCategoriesResponse(response: JSONObject): List<CategoryDataClass> {
        val categoryList = mutableListOf<CategoryDataClass>()
        val resultsArray = response.optJSONArray("results")

        resultsArray?.let {
            for (i in 0 until it.length()) {
                val categoryObject = it.getJSONObject(i)
                val name = categoryObject.optString("category_name")
                val category = CategoryDataClass(name)
                categoryList.add(category)
            }
        }

        return categoryList
    }


}

