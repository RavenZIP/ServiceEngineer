package com.example.serviceengineer.models

data class Instruction(
    var id: String,
    var title: String,
    var text: String,
    var authorId: String,
    var authorName: String,
    var dateCreate: String,
    var dateUpdate: String
){
    constructor(): this(
        id = "",
        title = "",
        text = "",
        authorId = "",
        authorName = "",
        dateCreate = "",
        dateUpdate = ""
    )
}
