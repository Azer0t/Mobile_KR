package com.example.kr.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.R
import com.example.kr.data.Repository
import com.example.kr.ui.adapters.MediaItemAdapter
import com.example.kr.viewmodel.MediaItemViewModel
import com.example.kr.viewmodel.MediaItemViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MediaItemListFragment : Fragment() {

    private lateinit var viewModel: MediaItemViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddItem: FloatingActionButton
    private lateinit var adapter: MediaItemAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_media_item_list, container, false)

        val factory = MediaItemViewModelFactory(Repository(requireContext()))
        viewModel = ViewModelProvider(this, factory).get(MediaItemViewModel::class.java)

        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        fabAddItem = rootView.findViewById(R.id.fabAddItem)
        searchView = rootView.findViewById(R.id.searchView)

        setupRecyclerView()
        setupListeners()
        setupSwipeRefresh()

        adapter = MediaItemAdapter(emptyList()) { selectedItem ->
            val bundle = Bundle().apply {
                putInt("itemId", selectedItem.id)
            }
            findNavController().navigate(R.id.action_mediaItemListFragment_to_mediaItemDetailsFragment, bundle)
        }
        recyclerView.adapter = adapter

        viewModel.mediaItems.observe(viewLifecycleOwner) { mediaItems ->
            adapter.updateItems(mediaItems)
            swipeRefreshLayout.isRefreshing = false
        }

        // Set up SearchView listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // No action on submit
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredItems = viewModel.mediaItems.value?.filter {
                    it.title.contains(newText ?: "", ignoreCase = true)
                } ?: emptyList()
                adapter.updateItems(filteredItems)
                return true
            }
        })

        return rootView
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupListeners() {
        fabAddItem.setOnClickListener {
            findNavController().navigate(R.id.action_mediaItemListFragment_to_addEditMediaItemFragment)
        }
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchMediaItems()
        }
    }
}
