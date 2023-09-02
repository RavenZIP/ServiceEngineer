package com.example.serviceengineer.models

data class RequestToCompany(
    val id: String,
    val userEmail: String,
    var validation: Boolean,
){
    constructor() : this(
        id = "",
        userEmail = "",
        validation = false
    )
}
