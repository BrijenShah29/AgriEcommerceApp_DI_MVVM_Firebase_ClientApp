package com.example.mapd726_groupproject_team3_agriapp

import android.content.Intent.getIntent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentOrderConfirmationBinding
import org.json.JSONObject


class OrderConfirmationFragment : Fragment() {

    lateinit var binding : FragmentOrderConfirmationBinding

    private lateinit var cartList : ArrayList<CartModel>
    private var totalPayment : String? = null
    private var customerAddress : String? = null
    private var customerName : String? = null
    private var customerNumber : String? = null
    private var paymentStatus : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderConfirmationBinding.inflate(layoutInflater)


        cartList = requireArguments().getParcelableArrayList<CartModel>("OrderItems")!!
        customerAddress = requireArguments().getString("OrderAddress")
        customerName = requireArguments().getString("OrderName")
        customerNumber = requireArguments().getString("OrderNumber")
        totalPayment = requireArguments().getString("OrderTotal")


        val jsonObject : JSONObject = JSONObject(requireArguments().getString("paymentDetails").toString())
        showPaymentDetails(jsonObject.getJSONObject("response"))







        return binding.root
    }

    private fun showPaymentDetails(jsonObject: JSONObject) {

    }

}