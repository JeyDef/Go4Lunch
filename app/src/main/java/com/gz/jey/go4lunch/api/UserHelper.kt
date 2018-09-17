package com.gz.jey.go4lunch.api

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.gz.jey.go4lunch.models.User
import java.util.*


object UserHelper {

    private const val COLLECTION_NAME : String = "users"

    // --- COLLECTION REFERENCE ---

    fun getUsersCollection() : CollectionReference {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME)
    }

    // --- CREATE ---

    fun createUser(uid : String, username : String, email: String, urlPicture : String, whereEatID : String, whereEatName : String, whereEatDate: String, restLiked : ArrayList<String>) : Task<Void> {
        val userToCreate = User(uid, username, email, urlPicture, whereEatID, whereEatName, whereEatDate, restLiked)
        return UserHelper.getUsersCollection().document(uid).set(userToCreate)
    }

    // --- UPDATE ---

    fun updateUser(uid : String, user : User) : Task<Void> {
        return UserHelper.getUsersCollection().document(uid).set(user)
    }

    /*fun updateContact(uid : String, contact : Contact) : Task<Void> {
        return UserHelper.getUsersCollection().document(uid).set(contact)
    }*/


    // --- GET ---

    fun getUser(uid : String) : Task<DocumentSnapshot>{
        return UserHelper.getUsersCollection().document(uid).get()
    }

}