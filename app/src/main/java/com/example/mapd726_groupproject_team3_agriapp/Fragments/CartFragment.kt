package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mapd726_groupproject_team3_agriapp.Adapter.CartAdapters.CartAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    lateinit var binding : FragmentCartBinding
     lateinit var cartProducts : List<CartModel>

    private val viewModel by viewModels<ProductsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        // GETTING ALL PRODUCTS FROM DATABASE

        // viewModel.getCartProducts()

        val cartAdapter = CartAdapter(requireContext(),viewModel)
        binding.cartProductsRecycler.adapter = cartAdapter
        //binding.cartProductsRecycler.setHasFixedSize(true)
        viewModel.cartProducts.observe(viewLifecycleOwner, Observer {
            cartAdapter.submitList(it)
            cartAdapter.notifyDataSetChanged()
            Log.d("cart Items",it.toString())

            calculateTotal(it)
        })






        return binding.root
    }

    private fun calculateTotal(products: List<CartModel>?) {
        var cartTotal = 0.0
        var totalTax = 0.0
        var totalPayable = 0.0
        if (products != null) {
            for(item in products){

                cartTotal += item.totalAmount!!
                totalTax += item.totalAmount * 0.13
            }
        }


        binding.totalTax.text = String.format("$ %.2f", totalTax)
        binding.cartTotal.text = String.format("$ %.2f", cartTotal)
        totalPayable = cartTotal+totalTax
        binding.totalPayable.text = String.format("$ %.2f",totalPayable)

    }


}