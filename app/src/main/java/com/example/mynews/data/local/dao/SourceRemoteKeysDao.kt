package com.example.mynews.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.data.local.entity.SourceRemoteKeysEntity

@Dao
interface SourceRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSourceRemoteKeys(remoteKey: List<SourceRemoteKeysEntity>?)

    @Query("SELECT * FROM SourceRemoteKeysTable WHERE id = :id")
    fun getSourceRemoteKeysId(id: String): SourceRemoteKeysEntity?

    @Query("DELETE FROM SourceRemoteKeysTable")
    suspend fun deleteSourceRemoteKeys()
}