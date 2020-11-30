package com.example.cemeterytracker.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cemeterytracker.R
import com.example.cemeterytracker.data.domain.asDomainCemList
import com.example.cemeterytracker.ui.adapters.CemeteryListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel : SearchCemsViewModel by viewModels()
    private lateinit var cemListAdapter: CemeteryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()

    }

    private fun setupListeners() {
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { viewModel.setSearchQuery(it) } //as user types searchQuery it is offered to subscribers which triggers repo.getSearchCemList
                    return true
                }
            }
        )
    }

    private fun setupObservers() {

        cemListAdapter = CemeteryListAdapter(CemeteryListAdapter.CemListListener {

            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToCemDetailFragment(it))
        })
        viewModel.cemeterySearchList.observe(viewLifecycleOwner){
            cemListAdapter.submitList(it.asDomainCemList())
        }

        searchCemsRv.adapter = cemListAdapter
    }


}