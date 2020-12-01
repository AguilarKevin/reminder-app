package com.aguilarkevin.reminder.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Entity
data class Event(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val dateInMills: Long,
    val type: String
)

@Dao
interface EventDao {

    @Query("SELECT COUNT(*) FROM Event")
    fun getEventCount(): Int

    @Query("SELECT * from Event order by dateInMills ASC")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("SELECT * from Event order by dateInMills ASC Limit 1")
    fun getNextEvent(): Event

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

        fun getDatabaseInBackground(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "events_database"
                ).allowMainThreadQueries().build()

                INSTANCE = instance
                return instance
            }
        }



        fun destroyInstance() {
            INSTANCE = null
        }
    }

}