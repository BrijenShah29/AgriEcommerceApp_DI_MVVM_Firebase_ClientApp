package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mapd726_groupproject_team3_agriapp.Adapter.ProductsListPageAdapters.CategoryProductAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.HomeViewModel
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentProductsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var productListBySubCategory = ArrayList<ProductModel>()

    private lateinit var binding : FragmentProductsBinding

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProductsBinding.inflate(layoutInflater)

      //  supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // supportActionBar?.setDisplayShowHomeEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val productAdapter = CategoryProductAdapter(requireContext())
        binding.categoryRecycler.adapter = productAdapter
        binding.categoryRecycler.layoutManager = GridLayoutManager(requireContext(),2)

        val productsList = requireArguments().getString("products")
        val field = requireArguments().getString("field")


        viewModel.getSelectedSubCategoryProducts("Products",field!!,productsList!!,productListBySubCategory)
        viewModel.selectedSubCategoryProducts.observe(viewLifecycleOwner, Observer {
            Log.d("Fetched products new",it.toString())
            productAdapter.submitList(it)
        })





        return binding.root
    }


    fun getProductsBySubCategoryFirebase(collectionPath: String, field: String, productCategory: String) : ArrayList<ProductModel> {

            val list = ArrayList<ProductModel>()
           val db = Firebase.firestore.collection(collectionPath).whereEqualTo(field, productCategory).get().addOnSuccessListener {
                for (doc in it.documents)
                { val firebaseData = doc.toObject(ProductModel::class.java)
                    list.add(firebaseData!!)
                }
            }
            return list
        }
    }
