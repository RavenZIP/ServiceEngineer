package com.example.serviceengineer.models

data class App (
    var version: String,
    var url: String,
){
    constructor(): this(
        "",
        ""
    )
}