package com.example.serviceengineer.models

data class Service(
    val id: String,
    val category: String,
    val service: String,
    val description: String,
    val price: String,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        ""
    )
}
