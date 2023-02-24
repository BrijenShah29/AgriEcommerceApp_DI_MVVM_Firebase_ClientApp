package com.example.mapd726_groupproject_team3_agriapp.DataModels

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "products")
@Parcelize
data class ProductModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = 0,
    val productName : String? = "",
    val productDescription : String? ="",
    val productCoverImg : String? ="",
    val productCategory: String? ="",
    val productSubCategory : String? ="",
    val productId : String?="",
    val productPrice : String? = "",
    val discountRate : String? = "",
    val stock : String? = "",
    val onSale : Boolean? = false,
    val productSpecialPrice : String? = "",
    val productScale : String? = "",
    val productImages : ArrayList<String> = ArrayList()
) :Parcelable{ }