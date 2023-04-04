package com.example.mapd726_groupproject_team3_agriapp.Fragments.UserProfileFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment() {

    lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutUsBinding.inflate(layoutInflater)

        binding.contactUs.setOnClickListener {
            findNavController().navigate(R.id.contactUsFragment)
        }

        return binding.root
    }
}
