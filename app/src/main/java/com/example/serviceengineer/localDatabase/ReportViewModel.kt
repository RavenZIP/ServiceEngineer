package com.example.serviceengineer.localDatabase

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.serviceengineer.models.LocalReport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<MutableList<LocalReport>>
    private val repository: ReportRepository

    init {
        val reportDao = LocalDb.getDatabase(application).ReportDao()
        repository = ReportRepository(reportDao)
        readAllData = repository.readAllReports
    }

    fun addTodo(report: LocalReport) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodo(report)
        }
    }

    fun updateTodo(report: LocalReport) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(report = report)
        }
    }

    fun deleteTodo(report: LocalReport) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(report = report)
        }
    }

    fun deleteAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTodos()
        }
    }
}

class ReportViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            return ReportViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}