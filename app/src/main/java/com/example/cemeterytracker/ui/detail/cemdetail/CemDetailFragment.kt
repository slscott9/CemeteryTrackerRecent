package com.example.cemeterytracker.ui.detail.cemdetail

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

@AndroidEntryPoint
class CemDetailFragment : Fragment() {

    private val viewModel: CemDetailViewModel by viewModels()
    private lateinit var binding: FragmentCemDetailBinding
    private val cemDetailFragmentArgs : CemDetailFragmentArgs by navArgs()
    private lateinit var graveListAdapter: GraveListAdapter


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

        binding.cemDetailToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }


        binding.cemeteryDetailViewModel = viewModel


        viewModel.setCemId(cemDetailFragmentArgs.cemId)

        graveListAdapter = GraveListAdapter(GraveListAdapter.GraveListListener {

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


}