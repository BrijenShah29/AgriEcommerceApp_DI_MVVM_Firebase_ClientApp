package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mapd726_groupproject_team3_agriapp.Activities.LoginActivity
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

    val viewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWishlistBinding.inflate(layoutInflater)

        userManager = UserManager(requireContext())

        userName = userManager.getUserName().toString()

        // SETTING UP GUEST VIEW FOR WISHLIST
        if(userName == Constant.USER_GUEST)
        {
            binding.guestUserViewLayout.visibility = View.VISIBLE
            // Redirect to Sign in page when On click

        }
        else
        {
            binding.guestUserViewLayout.visibility = View.GONE
        }

        // SETTING UP SIGN IN BUTTON FUNCTIONALITY
        binding.signInbutton.setOnClickListener {
            viewModel.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)

        }




        return binding.root
    }
}