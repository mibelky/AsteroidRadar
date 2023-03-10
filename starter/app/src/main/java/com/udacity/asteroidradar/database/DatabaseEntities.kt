package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.domain.PictureOfTheDay

/*
*   Entity kotlin data class, we operate objects of this class using Room DB
 */
@Entity(tableName = Constants.ASTEROIDS_TABLE_NAME)
data class DatabaseAsteroid(
    @PrimaryKey val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

/*
*   Extension function that convert list of objects from DB to the list of objects which we pass into UI
 */

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid (
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

/*
*   Entity kotlin data class, we operate objects of this class using Room DB
 */

@Entity(tableName = Constants.PICTURE_TABLE_NAME)
data class DatabasePictureOfTheDay(
    val mediaType: String,
    val title: String,
    @PrimaryKey val url: String
)

/*
*   Extension function that convert list of objects from DB to picture of the day object which we pass into UI
 */
fun List<DatabasePictureOfTheDay>.asDomainModel(): PictureOfTheDay? {

    val databasePictureOfTheDay = map {
        PictureOfTheDay (
            mediaType = it.mediaType,
            title = it.title,
            url = it.url
        )
    }

    if (databasePictureOfTheDay.isNotEmpty()) {
        return databasePictureOfTheDay.first()
    }

    return null

}