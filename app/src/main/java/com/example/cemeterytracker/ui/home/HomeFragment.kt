package com.example.cemeterytracker.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.cemeterytracker.R
import com.example.cemeterytracker.other.Constants
import com.example.cemeterytracker.other.Constants.KEY_LOGGED_IN_EMAIL
import com.example.cemeterytracker.other.Constants.KEY_PASSWORD
import com.example.cemeterytracker.other.Constants.NO_EMAIL
import com.example.cemeterytracker.other.Constants.NO_PASSWORD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var currentEmail: String ? = null
    private var currentPassword: String ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if(!isLoggedIn()){
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToLoginNavGraph())
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun isLoggedIn() : Boolean{
        //default value NO_EMAIL AND NO_PASSWORD
        //use return statement to check if user is logged in
        currentEmail = sharedPreferences.getString(
            KEY_LOGGED_IN_EMAIL,
            NO_EMAIL
        ) ?: NO_EMAIL
        currentPassword = sharedPreferences.getString(
            KEY_PASSWORD,
            NO_PASSWORD
        ) ?: NO_PASSWORD


        return currentEmail != NO_EMAIL && currentPassword != NO_PASSWORD
    }






}