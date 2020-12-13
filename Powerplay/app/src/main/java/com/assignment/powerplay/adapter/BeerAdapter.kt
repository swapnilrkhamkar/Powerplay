package com.assignment.powerplay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.assignment.powerplay.R
import com.assignment.powerplay.model.Beer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_beer.view.*
import java.util.*

class BeerAdapter(var beers: ArrayList<Beer>) :
    RecyclerView.Adapter<BeerAdapter.ViewHolder>() {

    lateinit var onItemClick: ((Beer) -> Boolean)
    lateinit var beersArrayList: ArrayList<Beer>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_beer, parent, false)

        beersArrayList = beers

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return beers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(beers[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnLongClickListener {
                onItemClick.invoke(beers[position])
            }
        }

        fun setData(@NonNull beer: Beer) {
            if (beer.name != null && !beer.name.isEmpty())
                itemView.txtBeerName.text = beer.name

            if (beer.tagline != null && !beer.tagline.isEmpty())
                itemView.txtBeerTag.text = beer.tagline

            if (beer.image_url != null && !beer.image_url.isEmpty())
                Picasso.get().load(beer.image_url).into(itemView.imgBeer);

        }

    }
}