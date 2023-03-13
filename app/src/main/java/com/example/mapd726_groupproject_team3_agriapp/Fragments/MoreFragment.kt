package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mapd726_groupproject_team3_agriapp.Activities.LoginActivity
import com.example.mapd726_groupproject_team3_agriapp.MainActivity
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.HomeViewModel
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentMoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment() {

    lateinit var binding : FragmentMoreBinding

    private val viewModel by viewModels<HomeViewModel>()
    private val viewModelProducts by viewModels<ProductsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(layoutInflater)

        binding.signOutButton.setOnClickListener {
            viewModel.signOut()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()

        }






        return binding.root
    }


}