package com.example.serviceengineer.models

data class Parameter(
    var id: String,
    var paramName: String,
    var paramType: String
){
    constructor() : this(
        id = "",
        paramName = "",
        paramType = ""
    )
}
