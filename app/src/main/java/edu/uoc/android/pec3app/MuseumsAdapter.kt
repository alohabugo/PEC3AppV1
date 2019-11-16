package edu.uoc.android.pec3app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.uoc.android.pec3app.models.Element
import kotlinx.android.synthetic.main.museum_row.view.*

class MuseumsAdapter(private val listMuseums: MutableList<Element>) : RecyclerView.Adapter<MuseumsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.museum_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listMuseums.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val museum = listMuseums[position]
        holder.adrecaNom.text = museum.getAdrecaNom()
        //get the image URL
        val urlImage = museum.getImatge().get(0)
        Picasso.get().load(urlImage).into(holder.imageMuseu);
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val adrecaNom: TextView = itemView.adrecaNom
        val imageMuseu: ImageView =  itemView.imageMuseu
    }
}
