package com.example.cemeterytracker.ui.login

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cemeterytracker.R
import com.example.cemeterytracker.other.Constants.KEY_LOGGED_IN_EMAIL
import com.example.cemeterytracker.other.Constants.KEY_LOGGED_IN_USERNAME
import com.example.cemeterytracker.other.Constants.KEY_PASSWORD
import com.example.cemeterytracker.other.Status
import com.example.cemeterytracker.ui.login.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSignUp.setOnClickListener {
            viewModel.register(userName = etRegisterUsername.text.toString(), userEmail = etRegisterEmail.text.toString(), password = etRegisterPassword.text.toString())

        }

        setupObservers()
    }

    private fun setupObservers() {

        viewModel.registerStatus.observe(viewLifecycleOwner){
            it?.let {
                when(it.status){
                    Status.SUCCESS -> {
                        registerProgressBar.visibility = View.GONE
                        sharedPreferences.edit().putString(KEY_LOGGED_IN_EMAIL, etRegisterEmail.text.toString()).apply()
                        sharedPreferences.edit().putString(KEY_LOGGED_IN_USERNAME, etRegisterUsername.text.toString()).apply()
                        sharedPreferences.edit().putString(KEY_PASSWORD, etRegisterPassword.text.toString()).apply()

                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                    }

                    Status.ERROR -> {
                        registerProgressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    Status.LOADING -> {
                        registerProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

    }


}