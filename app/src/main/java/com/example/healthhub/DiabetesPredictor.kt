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
import com.example.healthhub.databinding.ActivityDiabetesPredictorBinding
import org.json.JSONException
import org.json.JSONObject

class DiabetesPredictor : AppCompatActivity() {

    val url: String = BuildConfig.API_DIABETES

    private val binding by lazy {
        ActivityDiabetesPredictorBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        binding.progressBar.visibility = View.GONE

        binding.btnPredict.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val stringRequest = object : StringRequest(Method.POST, url,
                Response.Listener {
                    binding.progressBar.visibility = View.GONE
                    try {
                        val jsonObject = JSONObject(it)
                        val res = jsonObject.getString("Outcome")
                        if(res == "1") {
                            binding.textResult.text = getString(R.string.diaPos)
                        } else {
                            binding.textResult.text = getString(R.string.diaNeg)
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
                    params["Pregnancies"] = binding.pregnancies.text.toString()
                    params["Glucose"] = binding.glucose.text.toString()
                    params["BloodPressure"] = binding.bloodPressure.text.toString()
                    params["SkinThickness"] = binding.skinThickness.text.toString()
                    params["Insulin"] = binding.insulin.text.toString()
                    params["BMI"] = binding.bmi.text.toString()
                    params["DiabetesPedigreeFunction"] = binding.diabetesFunction.text.toString()
                    params["Age"] = binding.age.text.toString()

                    return params
                }
            }
            val queue: RequestQueue = Volley.newRequestQueue(this@DiabetesPredictor)
            queue.add(stringRequest)
        }
    }
}