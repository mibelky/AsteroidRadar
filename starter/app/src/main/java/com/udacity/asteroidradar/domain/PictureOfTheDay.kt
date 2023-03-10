package com.udacity.asteroidradar.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.udacity.asteroidradar.Constants
/* Pciture of the day class for representing object in the UI */
data class PictureOfTheDay(val mediaType: String, val title: String, val url: String)