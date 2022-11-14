package com.example.mynews.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.data.local.entity.SourceEntity

@Dao
interface SourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSource(allSourceEntity: List<SourceEntity>)

    @Query("SELECT * FROM SourceTable")
    fun getAllSource(): PagingSource<Int, SourceEntity>

    @Query("DELETE FROM SourceTable")
    suspend fun deleteAllSource()
}