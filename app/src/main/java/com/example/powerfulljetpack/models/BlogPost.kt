package com.example.powerfulljetpack.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blog_post")
data class BlogPost(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk")
    val pk: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "slug")
    val slug: String,

    @ColumnInfo(name = "body")
    val body: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "date_updated")
    val date_updated: Long,

    @ColumnInfo(name = "username")
    val username: String

)