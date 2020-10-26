package com.example.powerfulljetpack.ui.main.blog


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.models.BlogPost
import com.example.powerfulljetpack.ui.main.account.BlogListAdapter
import com.example.powerfulljetpack.ui.main.blog.state.BlogStateEvent
import com.example.powerfulljetpack.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_blog.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */

class BlogFragment : BaseBlogFragment(), BlogListAdapter.Interaction {


    @Inject
    lateinit var requestManager: RequestManager
    lateinit var recyclerAdapter: BlogListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        println("debugu: account fragment")

        initRecyclerView()

        subscribeObservers()
        executeSearch()
    }


    private fun executeSearch() {
        viewModel.setQuery("")
        viewModel.setStateEvent(
            BlogStateEvent.BlogSearchEvent()
        )
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            if (dataState != null) {
                dataStateChangeListener.onDataStateChange(dataState)
                dataState.data?.let {
                    it.data?.let { event ->
                        event.getContentIfNotHandled()?.let {

                            viewModel.setBlogListData(it.blogFields.blogList)
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            if (viewState != null) {
                recyclerAdapter.submitlist(

                    list = viewState.blogFields.blogList,
                    isQueryExhausted = true
                )
            }
        })
    }


    private fun initRecyclerView() {

        blog_post_recyclerview.apply {

            layoutManager = LinearLayoutManager(this@BlogFragment.context)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            //removing previus item decoration

            addItemDecoration(topSpacingItemDecoration)

            removeItemDecoration(topSpacingItemDecoration)
            recyclerAdapter = BlogListAdapter(requestManager, this@BlogFragment)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == recyclerAdapter.itemCount.minus(1)) {
                        //load next page using viewmodel
                        //pagination setup
                    }
                }
            })
            adapter = recyclerAdapter
        }

    }

    override fun onItemSelected(position: Int, item: BlogPost) {

        var x = 12
    }

    override fun restoreListPosition() {
        var x = 12
    }


    override fun onDestroy() {
        super.onDestroy()
        blog_post_recyclerview.adapter = null //avoid memory leaks
    }
}