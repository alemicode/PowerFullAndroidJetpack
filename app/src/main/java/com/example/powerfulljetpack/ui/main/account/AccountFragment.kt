package com.example.powerfulljetpack.ui.main.account


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.session.SessionManager
import kotlinx.android.synthetic.main.fragment_account.*
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



        setHasOptionsMenu(true)

        logout_button.setOnClickListener {
            sessionManager.logout()
        }

        change_password.setOnClickListener {

            findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
        }


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
