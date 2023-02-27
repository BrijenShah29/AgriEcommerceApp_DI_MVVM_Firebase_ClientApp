package com.example.mapd726_groupproject_team3_agriapp.Fragments.OtherFragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mapd726_groupproject_team3_agriapp.Adapter.SearchAdapter.SearchAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    lateinit var binding: FragmentSearchBinding
    lateinit var searchAdapter: SearchAdapter

    val viewModel by viewModels<ProductsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // setRecyclerView()
        searchAdapter = SearchAdapter(requireContext())
        binding.searchResultRecycler.layoutManager = GridLayoutManager(requireContext(),2)
        binding.searchResultRecycler.adapter = searchAdapter

        // Setting up Search View

        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(this)


        return binding.root
    }



    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }


    override fun onQueryTextChange(query: String?): Boolean {

        if(query!=null && query!=""){
            binding.searchResultRecycler.visibility = View.VISIBLE
            binding.noSearchFoundImg.visibility = View.GONE
            binding.noSearchFoundTxt.visibility = View.GONE
            searchDatabase(query)

        }
        else
        {
            binding.searchResultRecycler.visibility = View.GONE
            binding.noSearchFoundImg.visibility = View.VISIBLE
            binding.noSearchFoundTxt.visibility = View.VISIBLE

        }
        return true
    }

    private fun searchDatabase(query : String){
        val searchQuery = "%$query%"
            viewModel.searchDatabase(searchQuery)
            viewModel.searchedProducts.observe(viewLifecycleOwner, Observer {
                it.let {

                    searchAdapter.submitList(it)

                }
            })



    }
}