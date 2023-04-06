package com.example.mapd726_groupproject_team3_agriapp.DataModels

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull

@Entity(tableName = "wishlistTable")
@Parcelize
data class WishlistModel(
    val productName : String? = "",
    val productDescription : String? ="",
    val productCoverImg : String? ="",
    val productCategory: String? ="",
    val productSubCategory : String? ="",
    @PrimaryKey
    @NotNull
    val productId : String = "",
    val productPrice : String? = "",
    val discountRate : String? = "",
    val stock : String? = "",
    val onSale : Boolean? = false,
    val productSpecialPrice : String? = "",
    val productScale : String? = "",
    val productImages : ArrayList<String> = ArrayList()
) : Parcelable { }
