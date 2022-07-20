package com.saber.albums.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saber.albums.data.Album
import com.saber.albums.databinding.AlbumItemBinding


class AlbumsAdapter(private val albums: List<Album>) : RecyclerView.Adapter<AlbumViewHolder>() {

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun onBindViewHolder(
        holder: AlbumViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemBinding: AlbumItemBinding = AlbumItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlbumViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = albums.size

}
