package com.udacity.asteroidradar.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Constants
import kotlinx.android.parcel.Parcelize
/* Asteroid parcelable class for representing objects in the UI and sending between fragments via Bundle */
@Parcelize
data class Asteroid(val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable