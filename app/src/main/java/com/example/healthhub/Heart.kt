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
import com.example.healthhub.databinding.ActivityHeartBinding
import org.json.JSONException
import org.json.JSONObject

class Heart : AppCompatActivity() {

    val url: String = BuildConfig.API_HEART
    private var gender = 1

    private val binding by lazy {
        ActivityHeartBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        binding.progressBar.visibility = View.GONE

        //Checking for Gender.
        if(binding.genderButtonMale.isChecked) { gender = 1; }
        if(binding.genderButtonFemale.isChecked) { gender = 0; }

        binding.btnPredict.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val stringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener {
                    binding.progressBar.visibility = View.GONE
                    try {
                        val jsonObject = JSONObject(it)
                        val res = jsonObject.getString("output")
                        if(res == "1") {
                            binding.textResult.text = getString(R.string.heartPos)
                        } else {
                            binding.textResult.text = getString(R.string.heartNeg)
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
//                  age, sex, cp, trtbps, chol, fbs, restecg, thalachh, exng, oldpeak, slp, caa, thall
                    params["age"] = binding.age.text.toString()
                    params["sex"] = gender.toString()
                    params["cp"] = binding.chestPain.text.toString()
                    params["trtbps"] = binding.bloodResting.text.toString()
                    params["chol"] = binding.cholesterol.text.toString()
                    params["fbs"] = binding.bloodFasting.text.toString()
                    params["restecg"] = binding.ecg.text.toString()
                    params["thalachh"] = binding.heartRate.text.toString()
                    params["exng"] = binding.exercise.text.toString()
                    params["oldpeak"] = binding.oldPeak.text.toString()
                    params["slp"] = binding.slope.text.toString()
                    params["caa"] = binding.caa.text.toString()
                    params["thall"] = binding.stressTest.text.toString()

                    return params
                }
            }
            val queue: RequestQueue = Volley.newRequestQueue(this@Heart)
            queue.add(stringRequest)
        }
    }
}