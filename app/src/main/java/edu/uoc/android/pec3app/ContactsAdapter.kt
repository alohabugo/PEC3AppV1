package edu.uoc.android.pec3app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.uoc.android.pec3app.models.Contact
import kotlinx.android.synthetic.main.contact_row.view.*
import java.util.ArrayList

class ContactsAdapter(private val listContacts: ArrayList<Contact>): RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listContacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = listContacts[position]
        holder.nomContact.text = contact.getName()
        //get the image URL
        val urlImage = contact.getImageUrl()
        Picasso.get().load(urlImage).into(holder.imageContact);
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageContact: ImageView =  itemView.imageContact
        val nomContact: TextView = itemView.nomContact


    }
}