package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mapd726_groupproject_team3_agriapp.Activities.LoginActivity
import com.example.mapd726_groupproject_team3_agriapp.Adapter.HomePageAdapters.ProductAdapter
import com.example.mapd726_groupproject_team3_agriapp.Adapter.WishlistAdapter.WishlistAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.UserViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentWishlistBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WishlistFragment : Fragment() {

    lateinit var binding : FragmentWishlistBinding
    lateinit var userName : String
    @Inject
    lateinit var userManager: UserManager
    lateinit var productList : ArrayList<ProductModel>

    val viewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWishlistBinding.inflate(layoutInflater)

        userManager = UserManager(requireContext())

        userName = userManager.getUserName().toString()

        productList = ArrayList()

        // SETTING UP GUEST VIEW FOR WISHLIST
        if(userName == Constant.USER_GUEST)
        {
            binding.guestUserViewLayout.visibility = View.VISIBLE
            binding.wishlistProducts.visibility = View.GONE
        }
        else
        {
            binding.guestUserViewLayout.visibility = View.GONE
            binding.wishlistProducts.visibility = View.VISIBLE
            viewModel.getProductsOfWishlist()
        }

        // SETTING UP SIGN IN BUTTON FUNCTIONALITY
        binding.signInbutton.setOnClickListener {
            viewModel.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        val adapter = WishlistAdapter(requireContext())
        binding.wishlistProducts.adapter = adapter
        binding.wishlistProducts.layoutManager = GridLayoutManager(requireContext(),2)
        viewModel.wishlistProducts.observe(viewLifecycleOwner, Observer {
         productList.clear()
            if(it.isNotEmpty())
            {
                for(data in it)
                {
                    val product = ProductModel(
                        data.productName,
                        data.productDescription,
                        data.productCoverImg,
                        data.productCategory,
                        data.productSubCategory,
                        data.productId,
                        data.productPrice,
                        data.discountRate,
                        data.stock,
                        data.onSale,
                        data.productSpecialPrice,
                        data.productScale,
                        data.productImages
                    )
                    productList.add(product)
                }
            }
            adapter.submitList(productList)
        })

        return binding.root
    }

    override fun onPause() {
        viewModel.updateWishlistInServer(userManager.getUserId().toString())
        super.onPause()
    }
}