package com.example.mapd726_groupproject_team3_agriapp.Adapter.orderHistoryAdapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd726_groupproject_team3_agriapp.DataModels.OrderModel
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.UserViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.LayoutOrderHistoryBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class OrderHistoryParentAdapter(
    val context: Context,
    private val parentList: List<OrderModel>,
    val viewModel: UserViewModel
) : RecyclerView.Adapter<OrderHistoryParentAdapter.OrderHistoryParentViewHolder>() {

    inner class OrderHistoryParentViewHolder(val binding : LayoutOrderHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, parentModel: OrderModel, itemView: View)
        {
            binding.orderId.text = parentModel.orderId.toString()
            binding.orderTotal.text = parentModel.orderAmount.toString()
            // Date is pending
            val dateFormat = SimpleDateFormat("MM-dd-yyyy hh:mm", Locale.getDefault())
            if(parentModel.orderedDate!=null){
                val date = dateFormat.format(Date(parentModel.orderedDate))
                binding.orderDate.text = date
            }

            binding.orderStatus.text = parentModel.shipmentStatus

            binding.deliveryAddress.text = parentModel.shipmentAddress

            binding.totalOrderVisibilityButton.setOnClickListener {
                if(binding.orderItems.visibility == View.VISIBLE)
                {
                    binding.orderItems.visibility = View.GONE
                    binding.totalOrderVisibilityButton.setImageDrawable(context.getDrawable(com.stripe.android.R.drawable.mtrl_ic_arrow_drop_up))
                }
                else if(binding.orderItems.visibility == View.GONE){
                    binding.orderItems.visibility = View.VISIBLE
                    binding.totalOrderVisibilityButton.setImageDrawable(context.getDrawable(com.hbb20.R.drawable.ccp_ic_arrow_drop_down))
                }
            }
            when(parentModel.shipmentStatus){
                "Ordered" ->
                {
                    binding.cancelOrderButton.isEnabled = true
                }
                else -> {
                    binding.cancelOrderButton.isEnabled = false
                }
            }
            // Set up child RecyclerView
            binding.orderItems.adapter = OrderHistoryChildAdapter(this@OrderHistoryParentAdapter.context,parentModel.orderedProducts)
            binding.orderItems.layoutManager = LinearLayoutManager(this.itemView.context, LinearLayoutManager.HORIZONTAL, false)


            binding.cancelOrderButton.setOnClickListener {
                val alert = AlertDialog.Builder(context).setMessage("Are you sure about cancelling this Order?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") {dialog,id ->
                        viewModel.cancelOrder(parentModel.orderId)
                        CoroutineScope(Dispatchers.IO).launch {
                            delay(3000)
                            when (viewModel.cancelOrder.value) {
                                "Success" -> {
                                    Snackbar.make(itemView,"Order has been Cancelled successfully.",Snackbar.LENGTH_SHORT).show()
                                }
                                "Failed" -> {
                                    Snackbar.make(itemView,"Order can no be cancelled. Please try again.",Snackbar.LENGTH_LONG).show()
                                }
                                else -> {
                                    Toast.makeText( context, "Something went wrong.Please try again !!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                    }
                alert.create().show()
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryParentViewHolder {
        val view =  LayoutOrderHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderHistoryParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderHistoryParentViewHolder, position: Int) {
        val parentModel = parentList[position]
        holder.bind(context,parentModel,holder.itemView)
    }

    override fun getItemCount(): Int = parentList.size
}