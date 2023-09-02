package com.example.serviceengineer.models

data class Client (
    var clientName: String,
    var clientSurname: String,
    var clientPatronymic: String,
    var clientPhone: String,
    ){
    constructor(): this(
        "",
        "",
        "",
        ""
    )
}