package com.example.serviceengineer.models

data class Paper(
    var id: String,
    var name: String,
    var description: String,
    var text: List<String>,
    var images: List<String>,
    var dataSequence: String,
    var url: String,
    var dateCreate: String,
    var dateUpdate: String
    ){
    constructor(): this(
        id = "",
        name = "",
        description = "",
        text = listOf(),
        images = listOf(),
        dataSequence = "",
        url = "",
        dateCreate = "",
        dateUpdate = ""
    )
}
