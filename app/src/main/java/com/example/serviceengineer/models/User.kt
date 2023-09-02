package com.example.serviceengineer.models

data class User(
    var id: String,
    var name: String,
    var surname: String,
    var patronymic: String,
    var email: String,
    var type: String,
    var job: String,
    var post: String,
    //в лицензию записывается дата ее окончания
    var license: String
) {
    constructor() : this(
        id = "",
        name = "",
        surname = "",
        patronymic = "",
        email = "",
        type = "",
        job = "",
        post = "",
        license = ""
    )
}