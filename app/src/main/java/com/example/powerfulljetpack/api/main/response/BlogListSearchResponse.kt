package com.example.powerfulljetpack.api.main.response

import com.example.powerfulljetpack.models.BlogPost
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BlogListSearchResponse(

    @SerializedName("results")
    @Expose
    var results: List<BlogSearchResponse>,

    @SerializedName("detail")
    @Expose
    var detail: String
) {

//    fun toList(): List<BlogPost>{
//        val blogPostList: ArrayList<BlogPost> = ArrayList()
//        for(blogPostResponse in results){
//            blogPostList.add(
//                blogPostResponse.toBlogPost()
//            )
//        }
//        return blogPostList
//    }


    override fun toString(): String {
        return "BlogListSearchResponse(results=$results, detail='$detail')"
    }
}