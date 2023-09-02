package com.example.serviceengineer.localDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.serviceengineer.models.LocalReport

@Dao
interface ReportDao {
    @Query("SELECT * FROM LocalReports")
    fun getReports(): LiveData<MutableList<LocalReport>>

    @Query("SELECT * FROM LocalReports WHERE id = :id")
    fun getReportById(id: Int): LocalReport?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReport(report: LocalReport)

    @Update
    suspend fun updateReport(report: LocalReport)

    //@Query("DELETE FROM LocalReports WHERE id = :id")
    @Delete
    suspend fun deleteReport(report: LocalReport)

    @Query("DELETE FROM LocalReports")
    suspend fun deleteAllTodos()
}