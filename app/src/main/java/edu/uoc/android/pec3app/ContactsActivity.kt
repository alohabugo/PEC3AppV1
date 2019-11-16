package edu.uoc.android.pec3app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import edu.uoc.android.pec3app.models.Contact
import kotlinx.android.synthetic.main.activity_contacts.*
import java.util.ArrayList

class ContactsActivity : AppCompatActivity() {

    lateinit var listContacts: ArrayList<Contact> //list of returned contacts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        // Initialize Firebase
        //FirebaseApp.initializeApp(this)

        // Set persistence so that our app keeps its state
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        //get contacts list
        listContacts = FirebaseContactManager.getInstance().getAllContacts() as ArrayList<Contact>
        Log.d("contacts", "${listContacts}")

        //show in recyclerview
        //Initialize recyclerView
        recViewContacts.apply {
            layoutManager = LinearLayoutManager(this@ContactsActivity)
            adapter = ContactsAdapter(listContacts)
        }
    }
}
