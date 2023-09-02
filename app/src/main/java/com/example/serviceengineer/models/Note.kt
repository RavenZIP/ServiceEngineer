package com.example.serviceengineer.models

data class Note(
    val id: String,
    var title: String,
    var text: String,
) {
    constructor() : this(
        "",
        "",
        ""
    )
}