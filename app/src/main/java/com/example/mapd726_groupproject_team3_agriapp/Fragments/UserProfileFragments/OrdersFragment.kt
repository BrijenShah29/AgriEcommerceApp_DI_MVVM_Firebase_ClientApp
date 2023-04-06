package com.example.mapd726_groupproject_team3_agriapp.Fragments.UserProfileFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mapd726_groupproject_team3_agriapp.Adapter.orderHistoryAdapters.OrderHistoryParentAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.OrderModel
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.UserViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentOrdersBinding
import com.google.android.material.snackbar.Snackbar
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

        viewModel.fetchUserOrders(userManager.getUserId().toString())

//        val adapter = OrderHistoryParentAdapter(requireContext(),list,viewModel)
//        binding.orderList.adapter = adapter
        list.clear()
        viewModel.fetchUserOrders.observe(viewLifecycleOwner, Observer { orders->
            Log.d("orders","Received Orders")
            if(orders!=null)
            {
                val adapter = OrderHistoryParentAdapter(requireContext(),orders,viewModel,userManager.getUserId().toString())
                binding.orderList.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.cancelOrder.observe(viewLifecycleOwner,Observer{
            if(!it.isNullOrEmpty()) {
                if (it.toString() == "Success") {
                    Snackbar.make(
                        requireView(),
                        "Order has been Cancelled successfully.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else if (it.toString() == "Failed") {
                    Snackbar.make(
                        requireView(),
                        "Order can no be cancelled. Please try again.",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong.Please try again !!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })






        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // INITIATE ORDER LISTENERS
        viewModel.fetchUserOrders(userManager.getUserId().toString())


    }
}