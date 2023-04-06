package com.example.mapd726_groupproject_team3_agriapp.DataModels

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "OrderTable",indices = [Index(value = ["orderId"], unique = true)])
data class OrderModel(
    @PrimaryKey
    @ColumnInfo(name = "orderId")
    val orderId : String = "",
    val customerId : String? ="",
    val paymentStatus : String? = "",
    val orderedDate : Long? = 0,
    val orderAmount : String? = "",
    val shipmentStatus : String? ="",
    val shippingDate : String? = "",
    val shipmentAddress : String? ="",
    val shipmentReceiverName : String? = "",
    val shipmentReceiverEmail : String?="",
    val customerPhoneNumber : String? ="",
    val customerName : String?="",
    val orderedProducts : ArrayList<OrderedProductsModel>? = ArrayList()
)
