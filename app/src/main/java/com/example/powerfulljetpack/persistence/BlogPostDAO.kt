package com.example.powerfulljetpack.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.powerfulljetpack.models.BlogPost

@Dao
interface BlogPostDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blogPost: BlogPost): Long

    @Query("SELECT * FROM BLOG_POST")
    fun getAllBlogPosts(): LiveData<List<BlogPost>>

}