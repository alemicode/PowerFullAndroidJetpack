package com.example.powerfulljetpack.ui.main.blog


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.powerfulljetpack.R
import kotlinx.android.synthetic.main.fragment_blog.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */

class BlogFragment : BaseBlogFragment() {


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


        btn_blogfragment.setOnClickListener {

            println(
                "debug1 : ${findNavController().currentDestination.toString()
                }"
            )

            findNavController().navigate(R.id.action_blogFragment_to_viewBlogFragment)

        }
    }

}