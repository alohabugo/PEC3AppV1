package edu.uoc.android.pec3app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import edu.uoc.android.pec3app.models.Contact

class Splashscreen : AppCompatActivity(), ValueEventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
    }

    override fun onStart() {
        super.onStart()
        FirebaseContactManager.getInstance().getContactFromServer(this)
    }

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        for (contact in dataSnapshot.children) {
            FirebaseContactManager.getInstance().addContactHashMap(contact.getValue(Contact::class.java))
        }
        startMainActivity()
    }

    override fun onCancelled(databaseError: DatabaseError) {
        startMainActivity()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
