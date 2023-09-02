package com.example.serviceengineer.localDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.serviceengineer.models.LocalReport

@Database(entities = [LocalReport::class], version = 1, exportSchema = false)
abstract class LocalDb: RoomDatabase(){
    abstract fun ReportDao(): ReportDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDb? = null

        fun getDatabase(context: Context): LocalDb{
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDb::class.java,
                        "reportsDatabase"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}