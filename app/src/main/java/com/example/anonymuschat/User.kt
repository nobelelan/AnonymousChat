package com.example.anonymuschat

class User {
    var uid: String? = null
    var fullName: String? = null
    var email: String? = null

    constructor(){}


    constructor(uid: String?, fullName: String?, email: String?){
        this.uid = uid
        this.fullName = fullName
        this.email = email
    }
}