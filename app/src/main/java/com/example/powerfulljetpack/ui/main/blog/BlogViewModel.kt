package com.example.powerfulljetpack.ui.main.blog

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.bumptech.glide.RequestManager
import com.example.powerfulljetpack.models.BlogPost
import com.example.powerfulljetpack.persistence.BlogPostDAO
import com.example.powerfulljetpack.repository.main.BlogRepository
import com.example.powerfulljetpack.session.SessionManager
import com.example.powerfulljetpack.ui.BaseViewModel
import com.example.powerfulljetpack.ui.main.blog.state.BlogStateEvent
import com.example.powerfulljetpack.ui.main.blog.state.BlogViewState
import com.example.powerfulljetpack.util.AbsentLiveData
import com.example.powerfulljetpack.util.DataState
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class BlogViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val blogRepository: BlogRepository,
    private val sharedPreferences: SharedPreferences, //use for blog filters
    private val requestManager: RequestManager //user for glide
) : BaseViewModel<BlogStateEvent, BlogViewState>() {


    @InternalCoroutinesApi
    override fun handleStateEvent(stateEvent: BlogStateEvent): LiveData<DataState<BlogViewState>> {

        when (stateEvent) {
            is BlogStateEvent.BlogSearchEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    blogRepository.searchBlogPosts(
                        authToken,
                        viewState.value!!.blogFields.searchQuery
                    )
                }?:AbsentLiveData.create()
            }

            is BlogStateEvent.None -> {
                return AbsentLiveData.create()
            }


        }
    }

    override fun initNewStateEvent(): BlogViewState {

        return BlogViewState()

    }

    fun setQuery(query: String) {
        val update = getCurrentOrNewViewState()
//        if (query.equals(update.blogFields.blogList)) {
//            return
//        }
        update.blogFields.searchQuery = query
        _viewState.value = update

    }


    fun setBlogListData(bloglist: List<BlogPost>) {
        val update = getCurrentOrNewViewState()
        update.blogFields.blogList = bloglist
        _viewState.value = update
    }

    private fun cancellActiveJobs() {
        blogRepository.cancellActiveJobs()
        handlePendingData()
    }

    private fun handlePendingData() {
        setStateEvent(BlogStateEvent.None())
    }

    override fun onCleared() {
        super.onCleared()
        cancellActiveJobs()
    }
}