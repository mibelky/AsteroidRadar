package com.udacity.asteroidradar.api


import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.DatabasePictureOfTheDay

/**
 *  Container class for the list of NetworkAsteroid objects
 */

data class NetworkAsteroidContainer(val asteroids: List<NetworkAsteroid>)

/**
 *  NetworkAsteroid class in which fields we parse our JSON string from network request
 */

data class NetworkAsteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean)

/**
 *  Extension function that convert NetworkContainerObject into array of database objects
 */

fun NetworkAsteroidContainer.asDatabaseModel(): Array<DatabaseAsteroid> {
    return asteroids.map {
        DatabaseAsteroid(
            id = it.id,
            codename = it. codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous)
    }.toTypedArray()
}

/**
 *  Network container class for picture of the day object
 */

data class NetworkPictureOfTheDayContainer(val pictureOfTheDay: NetworkPictureOfTheDay)

/**
 *  Network picture of the day class that is used my Moshi to parse Json data into object of this class
 */
@JsonClass(generateAdapter = true)
data class NetworkPictureOfTheDay(
    val media_type: String,
    val title: String,
    val url: String
)
/**
 *  Extension function that convert NetworkContainerObject into database object
 */
fun NetworkPictureOfTheDayContainer.asDatabaseModel(): DatabasePictureOfTheDay {
    return DatabasePictureOfTheDay (
        this.pictureOfTheDay.media_type,
        this.pictureOfTheDay.title,
        this.pictureOfTheDay.url
    )
}