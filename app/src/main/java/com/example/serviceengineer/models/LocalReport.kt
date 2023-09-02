package com.example.serviceengineer.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LocalReports")
data class LocalReport(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "requestId")
    var requestId: String,
    @ColumnInfo(name = "reportText")
    var reportText: String,
    @ColumnInfo(name = "deviceFaultFinal")
    var deviceFaultFinal: String,
    @ColumnInfo(name = "renderedServices")
    var renderedServices: String,
    @ColumnInfo(name = "spareParts")
    var spareParts: String,
    @ColumnInfo(name = "sparePartsCount")
    var sparePartsCount: String,
    @ColumnInfo(name = "sum")
    var sum: String,
){
    constructor(): this(
        0,
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )
}
