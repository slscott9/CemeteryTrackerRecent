package com.example.cemeterytracker.ui.detail.gravedetail

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
import com.example.cemeterytracker.R
import com.example.cemeterytracker.databinding.FragmentGraveDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar.view.*

@AndroidEntryPoint
class GraveDetailFragment : Fragment() {

    private val viewModel: GraveDetailViewModel by viewModels()
    private val args : GraveDetailFragmentArgs by navArgs()
    private lateinit var binding : FragmentGraveDetailBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_grave_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.setGraveId(args.graveId)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarInclude.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.toolbarInclude.toolbar.title = "Grave"

        binding.editChip.setOnClickListener {
            findNavController().navigate(GraveDetailFragmentDirections.actionGraveDetailFragmentToAddEditGraveFragment(
                    graveId = args.graveId, //grave id is not null because we are updating a current grave
                    editGraveFlag = true,
                    cemId = viewModel.graveSelected.value!!.cemeteryId

            ))
        }


    }


}