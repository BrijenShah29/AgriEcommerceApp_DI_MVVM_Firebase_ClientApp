package com.example.mapd726_groupproject_team3_agriapp.DataModels

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull


@Entity(tableName = "cartProductTable")
@Parcelize
data class CartModel(
    val productName : String? = "",
    val productCoverImg : String? ="",
    val productCategory: String? ="",
    val productSubCategory : String? ="",
    @PrimaryKey
    @NotNull
    val productId : String,
    val productPrice : String? = "",
    val discountRate : String? = "",
    val onSale : Boolean? = false,
    val productSpecialPrice : String? = "",
    val productScale : String? = "",
    val productQuantity : Int ? = 0,
    val totalAmount : Double? = 0.0
) : Parcelable{
}
