package com.example.mynews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynews.data.local.dao.*
import com.example.mynews.data.local.entity.*

@Database(
    entities = [
        EverythingEntity::class,
        EverythingRemoteKeysEntity::class,
        SourceEntity::class,
        SourceRemoteKeysEntity::class,
        TopHeadlinesEntity::class,
        TopHeadlinesRemoteKeysEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun everythingDao(): EverythingDao
    abstract fun remoteKeysEverythingDao(): EverythingRemoteKeysDao
    abstract fun sourceDao(): SourceDao
    abstract fun remoteKeysSourceDao(): SourceRemoteKeysDao
    abstract fun topHeadlinesDao(): TopHeadlinesDao
    abstract fun remoteKeysTopHeadlinesDao(): TopHeadlinesRemoteKeysDao
}