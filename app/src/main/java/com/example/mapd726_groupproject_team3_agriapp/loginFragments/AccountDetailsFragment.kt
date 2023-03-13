package com.example.mapd726_groupproject_team3_agriapp.loginFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentAccountDetailsBinding
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentOtpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountDetailsFragment : Fragment() {

    lateinit var binding : FragmentAccountDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAccountDetailsBinding.inflate(layoutInflater)





        return binding.root
    }

}