package com.aguilarkevin.reminder.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    var title: String,
    var description: String,
    var date: String,
    var type: String
)

@Dao
interface EventDao {
    @Query("SELECT * from Event ")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("SELECT * from Event where date=:date")
    fun getEventPerDate(date: String): Event

    @Query("SELECT * from Event where id=:ID")
    fun getEvent(ID: Int): Event

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: Event)

    @Delete
    fun deleteEvent(event: Event)
}

@Database(entities = [Event::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "events_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}