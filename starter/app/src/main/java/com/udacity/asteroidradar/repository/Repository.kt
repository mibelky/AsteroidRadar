package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Filters
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfTheDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*

class Repository(private val database: AsteroidsDatabase) {

    private val _filter = MutableLiveData<Filters>(Filters.SAVED)

    fun updateFilter(filter: Filters?) {
        _filter.value = filter
    }

    val asteroidList: LiveData<List<Asteroid>> = Transformations.switchMap(_filter) { filter ->

        when (filter) {
            Filters.WEEK -> Transformations.map(
                database.asteroidDao.getAsteroids(
                    todayFormatted(),
                    dayWeekLaterFormatted()
                )
            ) { it.asDomainModel() }
            Filters.TODAY -> Transformations.map(database.asteroidDao.getAsteroids(todayFormatted())) { it.asDomainModel() }
            else -> Transformations.map(database.asteroidDao.getAll()) { it.asDomainModel() }
        }

    }

    val pictureOfTheDay: LiveData<PictureOfTheDay> = Transformations.map(database.pictureDao.getAllPicturesOfTheDay()) {
        it.asDomainModel()
    }

    suspend fun refreshPictureOfTheDay() {
        withContext(Dispatchers.IO) {
            val networkPictureOfTheDay = NetworkPictureOfTheDayContainer(AsteroidApi.retrofitService.getPictureOfDay())
            database.pictureDao.insertPictureOfTheDay(networkPictureOfTheDay.asDatabaseModel())
        }
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroidJSONObject = JSONObject(AsteroidApi.retrofitService.getAsteroids(todayFormatted()))
            val asteroidsList: ArrayList<NetworkAsteroid> = parseAsteroidsJsonResult(asteroidJSONObject)
            val networkAsteroids = NetworkAsteroidContainer(asteroidsList)
            database.asteroidDao.insertAll(*networkAsteroids.asDatabaseModel())
        }
    }


}
