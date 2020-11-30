package com.example.cemeterytracker.ui.detail.cemdetail

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cemeterytracker.R
import com.example.cemeterytracker.data.domain.asDomainGraveList
import com.example.cemeterytracker.databinding.FragmentCemDetailBinding
import com.example.cemeterytracker.ui.adapters.GraveListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.net.URI

@AndroidEntryPoint
class CemDetailFragment : Fragment() {

    private val viewModel: CemDetailViewModel by viewModels()
    private lateinit var binding: FragmentCemDetailBinding
    private val cemDetailFragmentArgs : CemDetailFragmentArgs by navArgs()
    private lateinit var graveListAdapter: GraveListAdapter

    val CemDetailFragment.packageManager get() = activity?.packageManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cem_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupListeners()

        binding.cemeteryDetailViewModel = viewModel


        viewModel.setCemId(cemDetailFragmentArgs.cemId)

        graveListAdapter = GraveListAdapter(GraveListAdapter.GraveListListener {
            findNavController().navigate(CemDetailFragmentDirections.actionCemDetailFragmentToGraveDetailFragment(it))
        })



        viewModel.cemeteryWithGraves.observe(viewLifecycleOwner){
            it?.let {
                graveListAdapter.submitList(it.graves.asDomainGraveList())
                binding.cemDetailToolbar.title = it.cemetery.name
            }
        }

        binding.graveRecyclerView.apply {
            adapter = graveListAdapter
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupListeners() {
        binding.addGraveChip.setOnClickListener {
            findNavController().navigate(CemDetailFragmentDirections.actionCemDetailFragmentToAddEditGraveFragment(cemId = cemDetailFragmentArgs.cemId))
        }

        binding.cemDetailToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.locationChip.setOnClickListener {


            val location = viewModel.cemeteryWithGraves.value?.cemetery?.location.toString()


            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$location")) //query for the location since lat and long may not always be exact

            intent.setPackage("com.google.android.apps.maps")

            //makes sure there is an app that can satisfy the intent
            intent.resolveActivity(packageManager!!)?.let {
                startActivity(intent)

            }
        }

    }


}