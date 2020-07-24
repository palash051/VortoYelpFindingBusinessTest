package com.jbhuiyan.projects.vortoyelptest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jbhuiyan.projects.vortoyelptest.R
import com.jbhuiyan.projects.vortoyelptest.businesslogic.Business
import com.jbhuiyan.projects.vortoyelptest.util.ListItemClickListener
import kotlinx.android.synthetic.main.item_photo.view.*

class BusinessListAdapter(
    var itemClickListener: ListItemClickListener,
    var businesses: ArrayList<Business>
) :
    RecyclerView.Adapter<BusinessListAdapter.PhotoViewHolder>() {

    companion object {
        const val CARD_TYPE_TEXT = "text"
        const val CARD_TYPE_TITLE_DESCRIPTION = "title_description"
        const val CARD_TYPE_IMAGE_TITLE_DESCRIPTION = "image_title_description"
    }

    fun updateCountries(newPhotoModels: List<Business>) {
        businesses.clear()
        businesses.addAll(newPhotoModels)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_photo, parent, false
        )
        return PhotoViewHolder(view, itemClickListener)
    }

    override fun getItemCount() = businesses.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(businesses[position])
    }


    class PhotoViewHolder(
        private val view: View,
        private val itemClickListener: ListItemClickListener
    ) : RecyclerView.ViewHolder(view) {
        fun bind(business: Business) {
            view.setOnClickListener { itemClickListener.onItemClick(business) }
            view.title.text= business.name
            view.address.text= business.location.displayAddress.joinToString(separator = " ")
            view.rating.rating= business.rating.toFloat()
            view.distance.text= "Distance: "+String.format("%.2f", business.distance!! / 1000) + " miles"
        }
    }
}

