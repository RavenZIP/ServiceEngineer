package com.example.serviceengineer.models

data class Request(
    var id: String,
    var finished: Boolean,
    var deviceType: String,
    var deviceManufacturer: String,
    var deviceModel: String,
    var deviceFault: String,
    var deviceKit: String,
    var deviceAppearance: String,
    //var clientPhone: String,
    var client: Client,
    var requestDate: String
) {
    constructor() : this(
        id = "",
        finished = false,
        deviceType = "",
        deviceManufacturer = "",
        deviceModel = "",
        deviceFault = "",
        deviceKit = "",
        deviceAppearance = "",
        //clientPhone = "",
        client = Client(),
        requestDate = ""
    )
}