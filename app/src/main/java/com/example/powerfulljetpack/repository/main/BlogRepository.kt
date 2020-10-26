package com.example.powerfulljetpack.repository.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.example.mvi.main.util.ApiSuccessResponse
import com.example.mvi.main.util.GenericApiResponse
import com.example.powerfulljetpack.api.main.OpenApiMainService
import com.example.powerfulljetpack.api.main.response.BlogListSearchResponse
import com.example.powerfulljetpack.api.main.response.BlogSearchResponse
import com.example.powerfulljetpack.models.AuthToken
import com.example.powerfulljetpack.models.BlogPost
import com.example.powerfulljetpack.persistence.BlogPostDAO
import com.example.powerfulljetpack.repository.JobManager
import com.example.powerfulljetpack.repository.NetworkBoundResource
import com.example.powerfulljetpack.session.SessionManager
import com.example.powerfulljetpack.ui.Response
import com.example.powerfulljetpack.ui.main.blog.state.BlogViewState
import com.example.powerfulljetpack.util.DataState
import com.example.powerfulljetpack.util.DateUtils
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class BlogRepository
@Inject
constructor(
    val openApiMainService: OpenApiMainService,
    val blogPostDAO: BlogPostDAO,
    val sessionManager: SessionManager
) : JobManager(
    "BlogRepository"
) {

    @InternalCoroutinesApi
    fun searchBlogPosts(
        authToken: AuthToken,
        query: String
    ): LiveData<DataState<BlogViewState>> {
        return object :
            NetworkBoundResource<BlogListSearchResponse, List<BlogPost>, BlogViewState>(
                sessionManager.isConnectedToTheInternet(),
                true,
                true,
                false
            ) {
            override suspend fun createCacheRequestAndReturn() {

                //it is on Main thread bcs we are refreshing liveData
                withContext(Main) {
                    result.addSource(loadFromCache()) { viewState ->
                        onCompleteJob(
                            DataState.data(
                                viewState,
                                null
                            )
                        )
                    }
                }
            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<BlogListSearchResponse>) {

                val blogPostList: ArrayList<BlogPost> = ArrayList()
                for (blogPost in response.body.results) {
                    blogPostList.add(
                        BlogPost(
                            pk = blogPost.pk,
                            title = blogPost.title,
                            slug = blogPost.slug,
                            body = blogPost.body,
                            image = blogPost.image,
                            date_updated = DateUtils.convertServerStringDateToLong(
                                blogPost.date_updated
                            ),
                            username = blogPost.username
                        )
                    )
                }
                updateLocalDb(blogPostList)
                createCacheRequestAndReturn()
            }

            override fun createCall(): LiveData<GenericApiResponse<BlogListSearchResponse>> {

                return openApiMainService.searchListBlogPost(
                    authorization = "Token ${authToken.token!!}",
                    query = query
                )
            }

            override fun loadFromCache(): LiveData<BlogViewState> {


                return blogPostDAO.getAllBlogPosts().switchMap {
                    object : LiveData<BlogViewState>() {
                        override fun onActive() {
                            super.onActive()
                            value = BlogViewState(
                                BlogViewState.BlogFields(
                                    blogList = it
                                )
                            )
                        }
                    }
                }
            }

            override suspend fun updateLocalDb(cacheObject: List<BlogPost>?) {


                if (cacheObject != null) {
                    withContext(IO) {
                        for (blogPost in cacheObject) {
                            try {
                                //launch each insert as a separate job to executed in parallel
                                val j = launch {

                                    blogPostDAO.insert(blogPost)
                                }
                            } catch (e: Exception) {
                                Log.e(

                                    "Error",
                                    "updateLocalDb: error updating cache: blogpost slut is : ${blogPost.slug}"

                                )
                            }
                        }
                    }
                }
            }

            override fun setJob(job: Job) {
                addjob("BlogRepository", job)
            }

        }.asLiveData()

    }
}