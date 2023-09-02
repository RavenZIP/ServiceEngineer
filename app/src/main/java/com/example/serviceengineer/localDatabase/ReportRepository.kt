package com.example.serviceengineer.localDatabase

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.serviceengineer.models.LocalReport

class ReportRepository (private val reportDao: ReportDao) {

    val readAllReports: LiveData<MutableList<LocalReport>> = reportDao.getReports()

    suspend fun addTodo(report: LocalReport) {
        reportDao.addReport(report)
    }

    suspend fun updateTodo(report: LocalReport) {
        reportDao.updateReport(report)
    }

    suspend fun deleteTodo(report: LocalReport) {
        reportDao.deleteReport(report)
    }

    suspend fun deleteAllTodos() {
        reportDao.deleteAllTodos()
    }
}