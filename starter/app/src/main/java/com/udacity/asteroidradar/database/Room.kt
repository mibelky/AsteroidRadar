package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

import com.udacity.asteroidradar.Constants

/* DB Dao interfaces with queries */

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM ${Constants.ASTEROIDS_TABLE_NAME} WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    fun getAsteroids(startDate: String, endDate: String = startDate) : LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)

    @Query("SELECT * FROM ${Constants.ASTEROIDS_TABLE_NAME} ORDER BY closeApproachDate ASC")
    fun getAll(): LiveData<List<DatabaseAsteroid>>

}

@Dao
interface PictureDao {
    @Query("SELECT * FROM ${Constants.PICTURE_TABLE_NAME}")
    fun getAllPicturesOfTheDay(): LiveData<List<DatabasePictureOfTheDay>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPictureOfTheDay(pictureOfTheDay: DatabasePictureOfTheDay)

}

/* DB class with Dao fields in it */
@Database(entities = [DatabaseAsteroid::class, DatabasePictureOfTheDay::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    abstract val pictureDao: PictureDao
}
/* The one and only DB instance for whole our application  */
private lateinit var INSTANCE: AsteroidsDatabase

fun getDatabase(context: Context): AsteroidsDatabase {
    synchronized(AsteroidsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AsteroidsDatabase::class.java,
                "asteroids").build()
        }
    }
    return INSTANCE
}