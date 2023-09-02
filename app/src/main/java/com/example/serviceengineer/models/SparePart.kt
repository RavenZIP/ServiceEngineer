package com.example.serviceengineer.models

class SparePart (
    val id: String,
    val category: String,
    val sparePart: String,
    val description: String,
    val price: String,
    val count: String,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        ""
    )
}