package com.example.mynews.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.data.local.entity.TopHeadlinesEntity

@Dao
interface TopHeadlinesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTopHeadlines(allTopHeadlinesEntity: List<TopHeadlinesEntity>)

    @Query("SELECT * FROM TopHeadlinesTable")
    fun getAllTopHeadlines(): PagingSource<Int, TopHeadlinesEntity>

    @Query("DELETE FROM TopHeadlinesTable")
    suspend fun deleteAllTopHeadlines()
}