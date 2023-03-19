package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mapd726_groupproject_team3_agriapp.Activities.LoginActivity
import com.example.mapd726_groupproject_team3_agriapp.Adapter.RecentlyVisitedProductsAdapter.RecentlyVisitedAdapter
import com.example.mapd726_groupproject_team3_agriapp.MainActivity
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.HomeViewModel
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.UserViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentMoreBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoreFragment : Fragment() {

    lateinit var binding : FragmentMoreBinding

    private val viewModel by viewModels<UserViewModel>()
    private val viewModelProducts by viewModels<ProductsViewModel>()

    @Inject
    lateinit var userManager : UserManager

    private lateinit var userName : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(layoutInflater)

        //SETTING UP RECENTLY VISITED PRODUCT RECYCLERVIEW
        val adapter = RecentlyVisitedAdapter(requireContext(),viewModelProducts)
        binding.productLastVisited.adapter = adapter
        binding.productLastVisited.setHasFixedSize(true)
        viewModelProducts.recentlyVisitedProducts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        userManager = UserManager(requireContext())

        userName = userManager.getUserName().toString()

        // SETTING UP GUEST VIEW FOR WISHLIST
        if(userName == Constant.USER_GUEST)
        {
            binding.guestUserViewLayout.visibility = View.VISIBLE
            binding.signedInUserViewLayout.visibility = View.GONE
            binding.signOutButton.visibility = View.GONE
            // Redirect to Sign in page when On click
        }
        else
        {
            binding.guestUserViewLayout.visibility = View.GONE
            binding.signedInUserViewLayout.visibility = View.VISIBLE
            binding.signOutButton.visibility = View.VISIBLE
        }

        // SIGN-OUT BUTTON ON CLICK FUNCTIONALITY

        binding.signOutButton.setOnClickListener {
            viewModel.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()

        }
        // GUEST USER SIGN IN ON CLICK
        binding.signInbutton.setOnClickListener {
            viewModel.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)

        }






        return binding.root
    }


}