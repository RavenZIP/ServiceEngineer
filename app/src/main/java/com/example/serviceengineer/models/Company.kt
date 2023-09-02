package com.example.serviceengineer.models

data class Company(
    val id: String,
    var name: String,
    var membersCount: String,
    var icon: String
) {
    constructor() : this(
        "",
        "",
        "",
        ""
    )
}
