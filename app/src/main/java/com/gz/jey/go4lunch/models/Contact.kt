package com.gz.jey.go4lunch.models

import java.util.*

class Contact(uid: String, username: String, urlPicture: String, whereEatID: String, whereEatName: String, restLiked: ArrayList<String>) {

    var uid: String = uid
    var username: String = username
    var urlPicture: String = urlPicture
    var whereEatID : String = whereEatID
    var whereEatName : String = whereEatName
    var restLiked : ArrayList<String> = restLiked
}