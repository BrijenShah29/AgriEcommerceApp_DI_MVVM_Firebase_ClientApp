package com.example.mapd726_groupproject_team3_agriapp.Fragments.UserProfileFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mapd726_groupproject_team3_agriapp.Adapter.orderHistoryAdapters.OrderHistoryParentAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.OrderModel
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.UserViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentOrdersBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrdersFragment : Fragment() {


    lateinit var binding: FragmentOrdersBinding

    lateinit var list : ArrayList<OrderModel>

    @Inject
    lateinit var auth : FirebaseAuth

    @Inject
    lateinit var userManager : UserManager

    val viewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrdersBinding.inflate(layoutInflater)
        list = ArrayList()

        val adapter = OrderHistoryParentAdapter(requireContext(),list,viewModel)
        binding.orderList.adapter = adapter
        viewModel.fetchUserOrders.observe(viewLifecycleOwner, Observer { orders->
            list.clear()
            if(orders!=null)
            {
                list = orders
            }
            adapter.notifyDataSetChanged()
        })




        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // INITIATE ORDER LISTENERS
        viewModel.fetchUserOrders(userManager.getUserId().toString())


    }
}