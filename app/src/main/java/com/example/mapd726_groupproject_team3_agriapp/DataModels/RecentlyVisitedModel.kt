package com.example.mapd726_groupproject_team3_agriapp.DataModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "RecentlyVisitedProductTable")
data class RecentlyVisitedModel(
    val productName : String? = "",
    val productCoverImg : String? ="",
    @PrimaryKey
    val productId : String = "",
    val productPrice : String? = "",
    val discountRate : String? = "",
    val onSale : Boolean? = false,
    val productSpecialPrice : String? = ""
)