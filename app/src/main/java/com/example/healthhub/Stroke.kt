package com.example.healthhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.healthhub.databinding.ActivityStrokeBinding
import org.json.JSONException
import org.json.JSONObject

class Stroke : AppCompatActivity() {
    val url: String = BuildConfig.API_STROKE
    private var gender = 1
    private var married = 1
    private var employed = 1
    private var residence = 1
    private var smoked = 1


    private val binding by lazy {
        ActivityStrokeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        binding.progressBar.visibility = View.GONE


        binding.btnPredict.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            checkRadioButtonStatus()
            val stringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener {
                    binding.progressBar.visibility = View.GONE
                    try {
                        val jsonObject = JSONObject(it)
                        val res = jsonObject.getString("stroke")
                        if(res == "1") {
                            binding.textResult.text = getString(R.string.strokePos)
                        } else {
                            binding.textResult.text = getString(R.string.strokeNeg)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }) {
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()

                    params["gender"] = gender.toString()
                    params["age"] = binding.age.text.toString()
                    params["hypertension"] = binding.hypertension.text.toString()
                    params["heart_disease"] = binding.heartDisease.text.toString()
                    params["ever_married"] = married.toString()
                    params["work_type"] = employed.toString()
                    params["Residence_type"] = residence.toString()
                    params["avg_glucose_level"] = binding.glucose.text.toString()
                    params["bmi"] = binding.bmi.text.toString()
                    params["smoking_status"] = smoked.toString()

                    return params
                }
            }
            val queue: RequestQueue = Volley.newRequestQueue(this@Stroke)
            queue.add(stringRequest)
        }
    }

    private fun checkRadioButtonStatus() {
        // Checking for Gender.
        if(binding.genderButtonMale.isChecked) { gender = 1 }
        if(binding.genderButtonFemale.isChecked) { gender = 0 }
        if(binding.genderButtonOther.isChecked) { gender = 2 }

        // Married Status
        if(binding.marriedYes.isChecked) { married = 1 }
        if(binding.marriedNo.isChecked) { married = 0 }

        // Employed Status
        if(binding.gov.isChecked) { employed = 0 }
        if(binding.privateSec.isChecked) { employed = 1 }
        if(binding.selfEmp.isChecked) { employed = 2 }
        if(binding.unEmp.isChecked) { employed = 3 }

        // Residence Status
        if(binding.rural.isChecked) { residence = 0 }
        if(binding.urban.isChecked) { residence = 1 }

        // Smoking Status
        if(binding.neverS.isChecked) { smoked = 0 }
        if(binding.formerlyS.isChecked) { smoked = 1 }
        if(binding.smokes.isChecked) { smoked = 2 }
        if(binding.unknown.isChecked) { smoked = 3 }
    }
}