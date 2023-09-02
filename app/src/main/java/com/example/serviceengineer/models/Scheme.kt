package com.example.serviceengineer.models

data class Scheme(
    var schemeId: String,
    var schemeName: String,
    var fileName: String,
    var schemeDescription: String,
    var authorId: String,
    var icon: String,
    var isVerified: Boolean
    ){
    constructor(): this(
        "",
        "",
        "",
        "",
        "",
        "",
        false
    )
}