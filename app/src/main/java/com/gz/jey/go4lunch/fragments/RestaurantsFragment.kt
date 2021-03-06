package com.gz.jey.go4lunch.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gz.jey.go4lunch.R
import com.gz.jey.go4lunch.activities.MainActivity
import com.gz.jey.go4lunch.models.Result
import com.gz.jey.go4lunch.utils.ItemClickSupport
import com.gz.jey.go4lunch.views.RestaurantsAdapter
import java.util.*

class RestaurantsFragment : Fragment(), RestaurantsAdapter.Listener{

    private var mView : View? = null

    // FOR DESIGN
    private var recyclerView: RecyclerView? = null

    var mainActivity: MainActivity? = null

    private var results: ArrayList<Result>? = null
    private var restaurantsAdapter: RestaurantsAdapter? = null

    /**
     * CALLED ON INSTANCE OF THIS FRAGMENT TO CREATE VIEW
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.recycler_view_fragment, container, false)
        mainActivity = activity as MainActivity
        recyclerView = mView!!.findViewById(R.id.recycler_view)
        return mView
    }

    /**
     * CALLED WHEN VIEW CREATED
     * @param view View
     * @param savedInstanceState Bundle
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setOnClickRecyclerView()
        updateUI()
    }

    /**
     * SET THE RECYCLER VIEW
     */
    private fun setRecyclerView() {
        results = ArrayList()
        // Create newsAdapter passing in the sample user data
        restaurantsAdapter = RestaurantsAdapter(
                        getString(R.string.google_api_key),
                        results!!,
                        mainActivity!!.contacts!!.size+1,
                        Glide.with(this),
                        this)

        // Attach the restaurantsAdapter to the recycler-view to populate items
        recyclerView!!.adapter = restaurantsAdapter
        // Set layout manager to position the items
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
    }

    // -----------------
    // ACTION
    // -----------------

    /**
     * SET ALL ONCLICKS FROM ITEMS IN RECYCLERVIEW
     */
    private fun setOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView!!, R.layout.restaurant_item)
                .setOnItemClickListener { _, position, _ ->
                    mainActivity!!.setLoading(false, true)
                    mainActivity!!.restaurantID = mainActivity!!.places!![position].place_id
                    mainActivity!!.restaurantName = mainActivity!!.places!![position].name
                    mainActivity!!.execRequest(mainActivity!!.CODE_DETAILS)
                }
    }

    /**
     * TO UPDATE RESTAURANT LIST
     */
    private fun updateUI() {
        if (results != null)
            results!!.clear()
        else
            results = ArrayList()

        results!!.addAll(mainActivity!!.places!!)

        if (results!!.size != 0) {
            mView!!.findViewById<TextView>(R.id.no_result_text).visibility = GONE
            restaurantsAdapter!!.notifyDataSetChanged()
        }else{
            view!!.findViewById<TextView>(R.id.no_result_text).visibility = VISIBLE
            view!!.findViewById<TextView>(R.id.no_result_text).text = getString(R.string.none_restaurant)
        }
        mainActivity!!.setLoading(false, false)
    }

    companion object {
        /**
         * @param mainActivity MainActivity
         * @return new RestaurantsFragment()
         */
        fun newInstance(mainActivity : MainActivity): RestaurantsFragment {
            val fragment = RestaurantsFragment()
            fragment.mainActivity = mainActivity
            return fragment
        }
    }
}