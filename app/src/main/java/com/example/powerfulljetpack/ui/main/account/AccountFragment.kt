package com.example.powerfulljetpack.ui.main.account


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.models.AccountProperties
import com.example.powerfulljetpack.session.SessionManager
import com.example.powerfulljetpack.ui.main.account.state.AccountStateEvent
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : BaseAccountFragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("debugu: account fragment")


        setHasOptionsMenu(true)


        subscribeObservers()
        logout_button.setOnClickListener {
            viewModel.logOut()

        }

        change_password.setOnClickListener {

            findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
        }


    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            dataStateChangeListener.onDataStateChange(dataState)
            dataState.let {


                it.data.let { data ->
                    data?.data.let {
                        it?.getContentIfNotHandled()?.let {
                            viewModel.setAccountPropertiesData(it)
                        }
                    }
                }


            }

        })

        viewModel.viewState.observe(this, Observer {


            it?.accountProperties.let {
                setAccountFields(it!!)

            }


        })
    }


    private fun setAccountFields(accountProperties: AccountProperties) {
        email.setText(accountProperties.email)
        username.setText(accountProperties.userName)
    }

    override fun onResume() {
        super.onResume()
        viewModel.setStateEvent(

            AccountStateEvent.GetAccountPropertiesEvent()
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_view_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                findNavController().navigate(R.id.action_accountFragment_to_updateAccountFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
