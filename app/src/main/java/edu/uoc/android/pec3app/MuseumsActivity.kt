package edu.uoc.android.pec3app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import edu.uoc.android.pec3app.models.Element
import edu.uoc.android.pec3app.models.Museums
import kotlinx.android.synthetic.main.activity_museums.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MuseumsActivity : AppCompatActivity() {

    val API_URL: String = "https://do.diba.cat"
    val PAG_INI: String = "1"
    val PAG_FI: String = "165"
    lateinit var listMuseums: MutableList<Element> //list of returned museums

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museums)
        progressBar.showProgress(true)

        //Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val museumService = retrofit.create(MuseumService::class.java)

        val call = museumService.museums(PAG_INI,PAG_FI)
        //call to the api and get the list of museums
        call.enqueue(object: Callback<Museums> {
            //send the request async
            override fun onResponse(call: Call<Museums>, response: Response<Museums>) {
                if (response.code() == 200) {
                    progressBar.showProgress(false)
                    val museums = response.body()!!
                    listMuseums = museums.getElements()
                    Log.d("museums", "onResponse ${listMuseums}")
                    //Initialize recyclerView
                    recViewMuseums.apply {
                        layoutManager = LinearLayoutManager(this@MuseumsActivity)
                        adapter = MuseumsAdapter(listMuseums)
                    }
                }
            }
            override fun onFailure(call: Call<Museums>, t: Throwable) {
                Log.d("museums", "onFailure" )
            }
        })
    }

    override fun onResume() {
        super.onResume()
        progressBar.showProgress(true)
    }

    private fun View.showProgress(b: Boolean) {
        visibility = if(b) {
            View.VISIBLE
        } else {
            Thread.sleep(2000); //time delay
            View.INVISIBLE
        }
    }
}
