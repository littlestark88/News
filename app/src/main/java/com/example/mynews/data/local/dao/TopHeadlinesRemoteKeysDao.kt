package com.example.mynews.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.data.local.entity.TopHeadlinesRemoteKeysEntity

@Dao
interface TopHeadlinesRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopHeadlinesRemoteKeys(remoteKey: List<TopHeadlinesRemoteKeysEntity>?)

    @Query("SELECT * FROM TopHeadlinesRemoteKeysTable WHERE id = :id")
    suspend fun getTopHeadlinesRemoteKeysId(id: String): TopHeadlinesRemoteKeysEntity?

    @Query("DELETE FROM TopHeadlinesRemoteKeysTable")
    suspend fun deleteTopHeadlinesRemoteKeys()
}