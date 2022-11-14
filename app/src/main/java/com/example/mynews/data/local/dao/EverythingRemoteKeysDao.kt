package com.example.mynews.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.data.local.entity.EverythingRemoteKeysEntity

@Dao
interface EverythingRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEverythingRemoteKeys(remoteKey: List<EverythingRemoteKeysEntity>?)

    @Query("SELECT * FROM EverythingRemoteKeysTable WHERE id = :id")
    suspend fun getEverythingRemoteKeysId(id: String): EverythingRemoteKeysEntity?

    @Query("DELETE FROM EverythingRemoteKeysTable")
    suspend fun deleteEverythingRemoteKeys()
}