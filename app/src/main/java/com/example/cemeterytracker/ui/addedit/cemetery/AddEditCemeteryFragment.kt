package com.example.cemeterytracker.ui.addedit.cemetery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.cemeterytracker.R
import com.example.cemeterytracker.databinding.FragmentAddEditCemeteryBinding
import kotlinx.android.synthetic.main.fragment_add_edit_cemetery.view.*
import kotlinx.android.synthetic.main.toolbar.view.*


class AddEditCemeteryFragment : Fragment() {

    private lateinit var binding: FragmentAddEditCemeteryBinding
    private val navArgs : AddEditCemeteryFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_cemetery, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if(navArgs.editFlag){

        }


        binding.addEditCemToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }


}