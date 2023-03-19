package com.example.mapd726_groupproject_team3_agriapp.Fragments.OtherFragments

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.mapd726_groupproject_team3_agriapp.Adapter.CartAdapters.CartAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentCartBinding
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentCartNavigatedBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.FragmentComponentManager
import java.util.ArrayList

@AndroidEntryPoint
class CartFragmentNavigated : Fragment() {

    private val viewModel by viewModels<ProductsViewModel>()

    private lateinit var binding: FragmentCartNavigatedBinding
    private lateinit var cartProducts : List<CartModel>

    var cartTotal = 0.0
    var totalTax = 0.0
    var totalPayable = 0.0
    var quantity  = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartNavigatedBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val cartAdapter = CartAdapter(requireContext(),viewModel)
        binding.cartProductsRecycler.adapter = cartAdapter
        //binding.cartProductsRecycler.setHasFixedSize(true)
        viewModel.cartProducts.observe(viewLifecycleOwner, Observer {
            quantity = 0
            cartAdapter.submitList(it)
            cartAdapter.notifyDataSetChanged()
            Log.d("cart Items",it.toString())

            cartProducts = it
            calculateTotal(it)

            for (products in it)
            {
                quantity += products.productQuantity!!.toInt()
            }
        })

        binding.checkoutButton.setOnClickListener {
            if(totalPayable == 0.0 || totalPayable < 1.0){

                val snack = Snackbar.make(requireView(),"Please add items into the cart", Snackbar.LENGTH_SHORT)
                snack.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                snack.show()
            }
            else
            {
                val bundle  = Bundle()
                bundle.putParcelableArrayList("CartProducts", cartProducts as ArrayList<out Parcelable>)
                bundle.putDouble("CartTotal",cartTotal)
                bundle.putDouble("cartTotalTax",totalTax)
                bundle.putDouble("CartTotalPayable",totalPayable)
                bundle.putInt("totalQuantity",quantity)

                val sender = (FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager
                //val sender = (requireContext() as FragmentActivity).supportFragmentManager
                sender.setFragmentResult("cartData",bundle)

                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_cartFragmentNavigated_to_shippingDetailsFragment,bundle)

            }
        }



        return binding.root
    }

    private fun calculateTotal(products: List<CartModel>?) {
        cartTotal = 0.0
        totalTax = 0.0
        totalPayable = 0.0
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