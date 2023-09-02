package com.example.serviceengineer.models

data class Report(
    var id: String,
    var requestId: String,
    var reportText: String,
    var deviceFaultFinal: String,
    var renderedServicesId: String,
    var sparePartsId: String,
    var sparePartsCount: String,
    var sum: String,
    var reportDate: String
){
    constructor(): this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )
}
