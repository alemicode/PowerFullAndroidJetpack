package com.example.powerfulljetpack.ui.main.account


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.models.AccountProperties
import com.example.powerfulljetpack.ui.main.MainActivity
import com.example.powerfulljetpack.ui.main.account.state.AccountStateEvent
import com.example.powerfulljetpack.util.SuccessHandling.Companion.RESPONSE_ACCOUNT_UPDATE_SUCCESS
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.input_email
import kotlinx.android.synthetic.main.fragment_update_account.*

/**
 * A simple [Fragment] subclass.
 */
class UpdateAccountFragment : BaseAccountFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_update_account, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setHasOptionsMenu(true)
        subscribeObservers()
    }


    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer {

            dataStateChangeListener.onDataStateChange(it)

            it.data?.let {
                it.response?.let {
                    if (it.peekContent().message.equals(RESPONSE_ACCOUNT_UPDATE_SUCCESS)) {
                        findNavController().popBackStack()
                    }
                }
            }
        })

        viewModel.viewState.observe(this, Observer { accountViewState ->

            if (accountViewState != null) {
                accountViewState.accountProperties?.let {

                    setAccountDataFields(it)
                }
            }
        })
    }


    private fun setAccountDataFields(accountProperties: AccountProperties) {

        input_email?.let {

            it.setText("${accountProperties.email}")
        }
        username_inp?.let {
            it.setText("${accountProperties.userName}")

        }

    }

    private fun saveChanges() {
        viewModel.setStateEvent(
            AccountStateEvent.UpdateAcountPropertiesEvent(
                input_email.text.toString(),
                username_inp.text.toString()
            )
        )
        dataStateChangeListener.hideSoftKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.update_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.save -> {
                saveChanges()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }
}

