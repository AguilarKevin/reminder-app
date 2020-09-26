package com.aguilarkevin.reminder.database

import androidx.room.*

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String?,
    val description: String,
    val type: String,
    val date: String
)

@Dao
interface EventDao {
    @Query("SELECT * from Event ")
    fun getAllEvents(): ArrayList<Event>

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

}