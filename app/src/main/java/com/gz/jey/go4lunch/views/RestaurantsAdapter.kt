package com.gz.jey.go4lunch.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.gz.jey.go4lunch.R
import com.gz.jey.go4lunch.models.Result
import java.util.*

class RestaurantsAdapter
/**
 * @param key String
 * @param results ArrayList<Result>
 * @param total Int
 * @param glide RequestManager
 * @param callback Listener */
(
        // FOR DATA
        private val key: String,
        private val results: ArrayList<Result>,
        private val total: Int,
        private val glide: RequestManager,
        // FOR COMMUNICATION
        private val callback: Listener) : RecyclerView.Adapter<RestaurantsViewHolder>() {

    interface Listener

    private var context: Context? = null

    /**
     * @param parent ViewGroup
     * @param viewType int
     * @return com.gz.jey.go4lunch.views.RestaurantsViewHolder(View)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.restaurant_item, parent, false)
        return RestaurantsViewHolder(view)
    }

    /**
     * @param viewHolder com.gz.jey.go4lunch.views.RestaurantsViewHolder
     * @param position int
     * UPDATE VIEW HOLDER WITH NEWS
     */
    override fun onBindViewHolder(viewHolder: RestaurantsViewHolder, position: Int) {
        viewHolder.updateRestaurants(this.key, context!!, this.total, this.results[position], this.callback)
    }

    /**
     * @return THE TOTAL COUNT OF ITEMS IN THE LIST
     */
    override fun getItemCount(): Int {
        return this.results.size
    }

}
