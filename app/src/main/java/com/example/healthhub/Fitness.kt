package com.example.healthhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.healthhub.databinding.ActivityFitnessBinding
import com.example.healthhub.databinding.ActivityStrokeBinding
import org.json.JSONException
import org.json.JSONObject

class Fitness : AppCompatActivity() {
    val url: String = BuildConfig.API_STROKE
    private var gender = 1


    private val binding by lazy {
        ActivityFitnessBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        binding.progressBar.visibility = View.GONE


        binding.btnPredict.setOnClickListener {
//            binding.progressBar.visibility = View.VISIBLE
            checkRadioButtonStatus()
            val stringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener {
//                    binding.progressBar.visibility = View.GONE
                    try {
                        val jsonObject = JSONObject(it)
                        val res = jsonObject.getString("Class")
                        binding.textResult.text = res.toString()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener {
//                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }) {
                override fun getParams(): Map<String, String>? {
                    val params = HashMap<String, String>()

                    params["age"] = binding.age.text.toString()
                    params["gender"] = gender.toString()
                    params["height_c1"] = binding.height.text.toString()
                    params["weight_kg"] = binding.weight.text.toString()
                    params["body 0at_%"] = binding.bodyFat.text.toString()
                    params["diastolic"] = binding.diastolic.text.toString()
                    params["systolic"] = binding.systolic.text.toString()
                    params["grip0orce"] = binding.grip.text.toString()
                    params["sit and bend 0orward_c1"] = binding.bending.text.toString()
                    params["sit-ups counts"] = binding.sitUps.text.toString()
                    params["broad ju1p_c1"] = binding.broadJump.text.toString()



                    return params
                }
            }
            val queue: RequestQueue = Volley.newRequestQueue(this@Fitness)
            queue.add(stringRequest)
        }
    }

    private fun checkRadioButtonStatus() {
        // Checking for Gender.
        if(binding.genderButtonMale.isChecked) { gender = 1 }
        if(binding.genderButtonFemale.isChecked) { gender = 0 }
    }
}