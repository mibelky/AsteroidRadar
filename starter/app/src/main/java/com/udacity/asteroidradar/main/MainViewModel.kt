package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Filters
import com.udacity.asteroidradar.domain.PictureOfTheDay
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException



class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application.applicationContext)
    private val asteroidsRepository = Repository(database)


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *  Actual asteroid list to represent in RecyclerView  *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    val asteroidList = asteroidsRepository.asteroidList

    /* * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Actual object containing picture of the day URI *
    * * * * * * * * * * * * * * * * * * * * * * * * * */

    val pictureOfDay: LiveData<PictureOfTheDay> = asteroidsRepository.pictureOfTheDay

    /* Found this solution of crashing app problem when launching it in airplane mode in the Mentors Help */

    init {
        viewModelScope.launch {
            try {
                asteroidsRepository.refreshAsteroids()
                asteroidsRepository.refreshPictureOfTheDay()
            } catch (e: UnknownHostException) { // Handle no internet
                Log.e("ViewModel", "Unknown host exception: $e");
            } catch (e: HttpException) { // Handle HTTP errors (like 400s, 500s)
                Log.e("ViewModel", "Http Exception: $e");
            } catch (e: IOException) { // Handle any other I/O errors
                Log.e("ViewModel", "IO exception: $e");
            } catch (e: SocketTimeoutException) { // Handle request timeout Exception
                Log.e("ViewModel", "Socket timeout exception: $e");
            }

        }

    }

    fun updateFilter(filter: Filters) {
        asteroidsRepository.updateFilter(filter)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}