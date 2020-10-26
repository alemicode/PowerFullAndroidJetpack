package com.example.powerfulljetpack.ui.main.blog.state

import com.example.powerfulljetpack.models.BlogPost

data class BlogViewState(

    // BlogFragmnet vars
    var blogFields: BlogFields = BlogFields()

    //ViewBlogFragments

    //updateBlogFragments

) {
    data class BlogFields(
        var blogList: List<BlogPost> = ArrayList<BlogPost>(),
        var searchQuery: String = ""
    )
}
