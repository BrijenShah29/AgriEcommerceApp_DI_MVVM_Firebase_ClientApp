package com.example.mapd726_groupproject_team3_agriapp.Fragments.OtherFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.mapd726_groupproject_team3_agriapp.Adapter.OrderConfirmationAdapter.OrderConfirmedAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentOrderConfirmationBinding

class OrderConfirmationFragment : Fragment() {

    lateinit var binding : FragmentOrderConfirmationBinding

    private lateinit var cartList : ArrayList<CartModel>
    private var totalPayment : String? = null
    private var customerAddress : String? = null
    private var customerName : String? = null
    private var customerNumber : String? = null
    private var orderDate : String? = null
    private var orderID: String? = null

    private var paymentStatus : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderConfirmationBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cartList = requireArguments().getParcelableArrayList<CartModel>("OrderItems")!!
        customerAddress = requireArguments().getString("OrderAddress")
        customerName = requireArguments().getString("OrderName")
        customerNumber = requireArguments().getString("OrderNumber")
        totalPayment = requireArguments().getString("OrderTotal")
        orderDate = requireArguments().getString("orderDate")
        orderID = requireArguments().getString("OrderId")

        binding.orderStatus.text = getString(R.string.order_status,"Order Placed")
        binding.orderDate.text = getString(R.string.order_date,orderDate)
        binding.receiverName.text = customerName
        binding.shippingAddress.text = customerAddress
        binding.shippingNumber.text = customerNumber
        binding.orderId.text = getString(R.string.orderid,orderID)

        val adapter = OrderConfirmedAdapter(requireContext())

        binding.orderedProductsRecycler.adapter = adapter
        binding.orderedProductsRecycler.setHasFixedSize(true)
        adapter.submitList(cartList)

        binding.continueButton.setOnClickListener {

            // navigate to main screen
            // clear popup
            findNavController().navigate(R.id.homeFragment,null,
                NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).build())



        }



        return binding.root
    }

}