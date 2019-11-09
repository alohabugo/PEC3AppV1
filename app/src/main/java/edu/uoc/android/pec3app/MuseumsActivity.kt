package edu.uoc.android.pec3app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_museums.*

class MuseumsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museums)

        recViewMuseums.apply {
            layoutManager = LinearLayoutManager(this@MuseumsActivity)
        }
    }
}
