package com.example.cemeterytracker.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cemeterytracker.R
import com.example.cemeterytracker.data.domain.asDomainCemList
import com.example.cemeterytracker.databinding.FragmentHomeBinding
import com.example.cemeterytracker.other.Constants
import com.example.cemeterytracker.other.Constants.KEY_LOGGED_IN_EMAIL
import com.example.cemeterytracker.other.Constants.KEY_PASSWORD
import com.example.cemeterytracker.other.Constants.NO_EMAIL
import com.example.cemeterytracker.other.Constants.NO_PASSWORD
import com.example.cemeterytracker.ui.adapters.CemeteryListAdapter
import com.example.cemeterytracker.ui.adapters.UserAddedCemListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val viewModel : HomeViewModel by viewModels()
    private lateinit var binding : FragmentHomeBinding

    private var currentEmail: String ? = null
    private var currentPassword: String ? = null

    private lateinit var cemListAdapter: CemeteryListAdapter
    private lateinit var userAddedCemListAdapter: UserAddedCemListAdapter

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapters()
        setupObservers()
        setupRv()
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

    private fun setupRv() {
        binding.rvUsersAddedCems.adapter = userAddedCemListAdapter

        binding.rvAllCems.apply {
            adapter = cemListAdapter
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupAdapters() {
        cemListAdapter = CemeteryListAdapter(CemeteryListAdapter.CemListListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCemDetailFragment(it))
        })

        userAddedCemListAdapter = UserAddedCemListAdapter(UserAddedCemListAdapter.UserAddedCemListListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCemDetailFragment(it))
        })
    }

    private fun setupObservers() {
        viewModel.userAddedCemList.observe(viewLifecycleOwner){

            it?.let {
                userAddedCemListAdapter.submitList(it.asDomainCemList())
            }
        }

        viewModel.allCems.observe(viewLifecycleOwner){
            it?.let{
                cemListAdapter.submitList(it.asDomainCemList())
            }
        }
    }






}