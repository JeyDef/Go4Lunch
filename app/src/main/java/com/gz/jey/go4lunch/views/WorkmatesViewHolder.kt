package com.gz.jey.go4lunch.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.gz.jey.go4lunch.R
import com.gz.jey.go4lunch.activities.MainActivity
import com.gz.jey.go4lunch.models.Contact
import com.gz.jey.go4lunch.models.Result
import com.gz.jey.go4lunch.models.User
import com.gz.jey.go4lunch.utils.ApiPhoto
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.ref.WeakReference

class WorkmatesViewHolder
/**
 * @param itemView View
 */
internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var name: TextView? = null
    private var photo: CircleImageView? = null
    private var action: TextView? = null
    private var restaurant: TextView? = null

    private var callbackWeakRef: WeakReference<WorkmatesAdapter.Listener>? = null

    init {
        name = itemView.findViewById(R.id.name)
        photo = itemView.findViewById(R.id.photo)
        action = itemView.findViewById(R.id.action)
        restaurant = itemView.findViewById(R.id.restaurant)
    }

    /**
     * @param contact MyContacts
     * @param glide RequestManager
     * @param callback NewsAdapter.Listener
     * UPDATE NEWS ITEM LIST
     */
    fun updateWorkmates(key: String, activity : MainActivity, context : Context, contact: Contact, callback: WorkmatesAdapter.Listener) {

        val goEat = context.getString(R.string.go_eat).toString()
        val dontChoosen = context.getString(R.string.doesnt_chosen).toString()
        var restName = ""

        for (r in activity.place!!.results){
            if(r.id==contact.whereEat) {
                restName = r.name
                break
            }
        }

        this.name!!.text = contact.username
        this.action!!.text = if(!contact.whereEat.isEmpty()) goEat else dontChoosen
        this.restaurant!!.text = if(!contact.whereEat.isEmpty()) restName else ""

        val imgLink = contact.urlPicture
        GlideApp.with(context)
                .load(imgLink)
                .into(photo!!)

        callbackWeakRef = WeakReference(callback)
    }

    /**
     * @param view View
     * OnClick callback
     */
    override fun onClick(view: View) {
        callbackWeakRef!!.get()
    }
}