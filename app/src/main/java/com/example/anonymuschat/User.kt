package com.example.anonymuschat

class User {
    var uid: String? = null
    var fullName: String? = null
    var email: String? = null
    var imageUrl: String? = null

    constructor(){}


    constructor(uid: String?, fullName: String?, email: String?, imageUrl: String?){
        this.uid = uid
        this.fullName = fullName
        this.email = email
        this.imageUrl = imageUrl
    }
}