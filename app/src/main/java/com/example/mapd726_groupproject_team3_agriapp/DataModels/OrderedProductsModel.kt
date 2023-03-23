package com.example.mapd726_groupproject_team3_agriapp.DataModels

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "OrderedProductTable")
data class OrderedProductsModel(
    @PrimaryKey
    @NonNull
    var productId : String,
    var orderedProductName : String? ="",
    var orderedProductQuantity : String ? = "1",
    var productSellingPrice : String ? = "",
    var productImage : String? =""
) {
}