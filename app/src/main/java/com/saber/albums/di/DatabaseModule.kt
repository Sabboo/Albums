package com.saber.albums.di

import android.content.Context
import androidx.room.Room
import com.saber.albums.db.AlbumsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): AlbumsDb {
        val databaseBuilder = Room.databaseBuilder(appContext, AlbumsDb::class.java, "albums.db")
        return databaseBuilder
            .fallbackToDestructiveMigration()
            .build()
    }
}