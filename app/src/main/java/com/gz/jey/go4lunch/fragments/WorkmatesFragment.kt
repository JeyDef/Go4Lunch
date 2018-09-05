package com.gz.jey.go4lunch.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gz.jey.go4lunch.R
import com.gz.jey.go4lunch.activities.MainActivity
import com.gz.jey.go4lunch.models.Contact
import com.gz.jey.go4lunch.utils.ItemClickSupport
import com.gz.jey.go4lunch.views.WorkmatesAdapter
import io.reactivex.disposables.Disposable
import java.util.*

class WorkmatesFragment : Fragment(), WorkmatesAdapter.Listener{

    private var mView : View? = null

    // FOR DESIGN
    private var recyclerView: RecyclerView? = null

    var mainActivity: MainActivity? = null
    private var disposable: Disposable? = null

    private var contacts: ArrayList<Contact>? = null
    private var workmatesAdapter: WorkmatesAdapter? = null

    /**
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setOnClickRecyclerView()
        initList()
    }

    /**
     * to set the RecyclerView
     */
    private fun setRecyclerView() {
        contacts = ArrayList()
        // Create newsAdapter passing in the sample user data
        workmatesAdapter = WorkmatesAdapter(
                contacts!!,
                Glide.with(this),
                this)

        // Attach the workmatesAdapter to the recycler-view to populate items
        recyclerView!!.adapter = workmatesAdapter
        // Set layout manager to position the items
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
    }


    // -----------------
    // ACTION
    // -----------------

    /**
     * to Set the onClick function from items in RecyclerView
     */
    private fun setOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView!!, R.layout.workmates_item)
                .setOnItemClickListener { _, position, _ ->
                    val rid = mainActivity!!.contacts[position].whereEatID
                    val nam = mainActivity!!.contacts[position].whereEatName
                    if(!rid.isEmpty()){
                        mainActivity!!.setLoading(false, true)
                        mainActivity!!.restaurantID = rid
                        mainActivity!!.restaurantName = nam
                        mainActivity!!.setFragment(4)
                    }else{
                        val cont = mainActivity!!.contacts[position].username
                        val unch = getString(R.string.doesnt_chosen)
                        mainActivity!!.popupmsg("$cont $unch")
                    }
                }
    }

    private fun initList() {
        updateUI(mainActivity!!.contacts)
    }

    /**
     * @param cntc ArrayList<Contact>
     * called while request get back models
     */
    fun updateUI(cntc : ArrayList<Contact>) {
        if (contacts != null)
            contacts!!.clear()
        else
            this.contacts = ArrayList()

        contacts!!.addAll(cntc)

        if (contacts!!.size != 0) {
            mView!!.findViewById<TextView>(R.id.no_result_text).visibility = View.GONE
            workmatesAdapter!!.notifyDataSetChanged()
        }else{
            view!!.findViewById<TextView>(R.id.no_result_text).visibility = View.VISIBLE
            view!!.findViewById<TextView>(R.id.no_result_text).text = getString(R.string.none_workmate)
        }
        mainActivity!!.setLoading(false, false)
    }

    /**
     * to Destroy fragment
     */
    override fun onDestroy() {
        super.onDestroy()
        disposeWhenDestroy()
    }

    /**
     * to destroy disposable and avoid memory leaks
     */
    private fun disposeWhenDestroy() {
        if (disposable != null && !disposable!!.isDisposed)
            disposable!!.dispose()
    }

    companion object {
        /**
         * @param mainActivity MainActivity
         * @return new WorkmatesFragment()
         */
        fun newInstance(mainActivity : MainActivity): WorkmatesFragment {
            val fragment = WorkmatesFragment()
            fragment.mainActivity = mainActivity
            return fragment
        }
    }
}