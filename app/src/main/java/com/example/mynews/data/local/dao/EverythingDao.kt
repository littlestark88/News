package com.example.mynews.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.data.local.entity.EverythingEntity

@Dao
interface EverythingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEverything(allEverythingEntity: List<EverythingEntity>)

    @Query("SELECT * FROM EverythingTable")
    fun getAllEverything(): PagingSource<Int, EverythingEntity>

    @Query("DELETE FROM EverythingTable")
    suspend fun deleteAllEverything()
}