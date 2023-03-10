package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidViewItemBinding

class AsteroidListAdapter(private val asteroidClickListener: AsteroidClickListener) :
    ListAdapter<Asteroid, AsteroidListAdapter.AsteroidViewHolder>(DiffCallback) {


    /* * * * * * * * * * * * * * * * * * * * * *
    *  Diffcallback methods for Asteroid items *
    * * * * * * * * * * * * * * * * * * * * *  */

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
            override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem.id == newItem.id
            }
        }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  ViewHolder implementation for AsteroidRecyclerView  *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    class AsteroidViewHolder private constructor(private var binding: AsteroidViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        companion object {
            fun from(parent: ViewGroup) = AsteroidViewHolder(
                AsteroidViewItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }

    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Creating new ViewHolder -> inflating item layout    *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AsteroidViewHolder.from(parent)

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *  Binding item to ViewHolder, setting OnClick, binding asteroid item  *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid)
        holder.itemView.setOnClickListener {
            asteroidClickListener.onClick(asteroid)
        }
    }

    class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}

