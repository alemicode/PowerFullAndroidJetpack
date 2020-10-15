package com.example.powerfulljetpack.ui.main.account


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.ui.main.account.state.AccountStateEvent
import com.example.powerfulljetpack.util.SuccessHandling.Companion.RESPONSE_PASSWORD_UPDATE_SUCCESS
import kotlinx.android.synthetic.main.fragment_change_password.*
import java.util.zip.Inflater

/**
 * A simple [Fragment] subclass.
 */
class ChangePasswordFragment : BaseAccountFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        observable()
        update_password_button.setOnClickListener {

            viewModel.setStateEvent(
                AccountStateEvent.ChangePasswordEvent(
                    input_current_password.text.toString(),
                    input_confirm_new_password.text.toString(),
                    input_confirm_new_password.text.toString()
                )
            )
        }

    }


    private fun observable() {
        viewModel.dataState.observe(this, Observer { dataState ->

            dataStateChangeListener.onDataStateChange(dataState)
            if (dataState != null) {

                dataState.data?.let {

                    it.response?.let { event ->
                        event.peekContent().message?.let { msgResponse ->

                            if (msgResponse.equals(RESPONSE_PASSWORD_UPDATE_SUCCESS)) {
                                dataStateChangeListener.hideSoftKeyboard()
                                findNavController().popBackStack()
                            }
                        }

                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        return inflater.inflate(R.menu.update_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            //is R.id.
        }
        return super.onContextItemSelected(item)

    }
}

