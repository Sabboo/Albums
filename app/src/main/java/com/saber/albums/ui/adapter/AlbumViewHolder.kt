package com.saber.albums.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.saber.albums.data.Album
import com.saber.albums.databinding.AlbumItemBinding


class AlbumViewHolder(private val albumItemBinding: AlbumItemBinding) :
    RecyclerView.ViewHolder(albumItemBinding.root) {

    fun bind(album: Album?) {
        albumItemBinding.albumTitle.text = album?.title
    }

}