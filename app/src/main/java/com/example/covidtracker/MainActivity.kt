package com.example.covidtracker

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.covidtracker.databinding.ActivityMainBinding
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding


    lateinit var sateList : ArrayList<String>
    lateinit var cityList : ArrayList<trackerData>

    private lateinit var stateRV : RecyclerView

    private lateinit var dateAdapter :rvAdapter





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // updated the global info
        getGlobalInfo()


        // updat's the data of india
        getIndiaInfo()

        // State array initialization
        sateList = ArrayList<String>()
        sateList.add("Select State")
        //state array updation
        getStateInfo()

//          spinner
        val spinner = binding.spinnertest

        spinner.adapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1,sateList)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2!=0){

                    // city display function
                    getCityInfo(sateList[p2])
                }



            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext , "Noting Selectd" , Toast.LENGTH_LONG).show()

            }

        }










    }



    private fun getStateInfo(){
        val url = "https://data.covid19india.org/state_district_wise.json"
        val queue = Volley.newRequestQueue(this)
        val req =
            JsonObjectRequest(Request.Method.GET, url, null, { response ->
                try {
                    val lists = response.names()!!
                    for(i in 1 until lists.length()){

                        val data = lists[i].toString()

                        sateList.add(data)





                    }
                    Log.d("TAG" , "State List Updated")

                }catch (e:JSONException){

                    Toast.makeText(this , "Error! " , Toast.LENGTH_LONG).show()
                    Log.d("TAG" , "catch")


                    e.printStackTrace()
                }
            },
                { error ->
                    Toast.makeText(this , "failed! Check your connection" , Toast.LENGTH_LONG).show()
                    Log.d("TAG" , "failed to get data")

                }
            )
        queue.add(req)
    }


    private fun getCityInfo(state : String){
        cityList = ArrayList()
        stateRV = binding.stateRV
        val url = "https://data.covid19india.org/state_district_wise.json"
        val queue = Volley.newRequestQueue(this)
        val req =
            JsonObjectRequest(Request.Method.GET, url, null, { response ->
                try {




                    val dataobj = response.getJSONObject(state).getJSONObject("districtData")

                    val arrofCity = dataobj.names()!!


                    for(i in 0 until arrofCity.length()){

                        var cityName = arrofCity[i].toString()

                        val regObj  = dataobj.getJSONObject(cityName)
                        val cases = regObj.getInt("confirmed")

                        val recovered = regObj.getInt("recovered")
                        val death = regObj.getInt("deceased")
                        var live = regObj.getInt("active")
                        val status = regObj.getString("notes").isEmpty()
                        if(cityName=="Unknown"){
                            cityName="Remaining Cities"
                        }
                        if(cityName=="Others"){
                            continue
                        }

                        if(live<0){
                            live=live*-1
                        }
                        Log.d("Volly" , "$status  : $cityName")



                        val stateModel = trackerData(cityName , cases,recovered,death,live, !status)
                        cityList.add(stateModel)

                    }
//
                    dateAdapter = rvAdapter(this , cityList)
                    stateRV.layoutManager = LinearLayoutManager(this)
                    stateRV.adapter = dateAdapter


                    Log.d("TAG" , "City data Displayed")

                }catch (e:JSONException){

                    Toast.makeText(this , "Error! " , Toast.LENGTH_LONG).show()
                    Log.d("TAG" , "catch")


                    e.printStackTrace()
                }
            },
                { error ->
                    Toast.makeText(this , "failed! Check your connection" , Toast.LENGTH_LONG).show()
                    Log.d("TAG" , "failed to get data")

                }
            )
        queue.add(req)
    }

    private fun getIndiaInfo(){

        val url = "https://api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(this)
        val req =
            JsonObjectRequest(Request.Method.GET, url, null, { response ->
                try {

                    val indobj = response.getJSONObject("data").getJSONObject("summary")

                    binding.Indiacases.text = indobj.getInt("total").toString()
                    binding.INdiarecovered.text = indobj.getInt("discharged").toString()
                    binding.Indiadeaths.text = indobj.getInt("deaths").toString()

                    Log.d("TAG" , "India data Updated")


                }catch (e:JSONException){

                    Toast.makeText(this , "Error! " , Toast.LENGTH_LONG).show()
                    Log.d("TAG" , "catch")


                    e.printStackTrace()
                }
            },
                { error ->
                    Toast.makeText(this , "failed! Check your connection" , Toast.LENGTH_LONG).show()
                    Log.d("TAG" , "failed to get data")

                }
            )
        queue.add(req)

    }

    private fun getGlobalInfo(){

        val url = "https://corona.lmao.ninja/v3/covid-19/all"
        val queue = Volley.newRequestQueue(this)
        val req =
            JsonObjectRequest(Request.Method.GET, url, null, { response ->
                try {

                    binding.WWcases.text = response.getInt("cases").toString()
                    binding.WWdeaths.text = response.getInt("deaths").toString()
                    binding.WWrecovered.text = response.getInt("recovered").toString()

                    Log.d("TAG" , "World data Updated")


                }catch (e:JSONException){

                    Toast.makeText(this , "Error! " , Toast.LENGTH_LONG).show()
                    Log.d("TAG" , "catch")


                    e.printStackTrace()
                }
            },
                { error ->
                    Toast.makeText(this , "failed! Check your connection" , Toast.LENGTH_LONG).show()
                    Log.d("TAG" , "failed to get data")

                }
            )
        queue.add(req)

    }
}