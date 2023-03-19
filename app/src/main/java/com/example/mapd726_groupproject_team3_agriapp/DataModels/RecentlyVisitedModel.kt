package com.example.mapd726_groupproject_team3_agriapp.DataModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.sql.Timestamp
import java.util.*

@Entity(tableName = "RecentlyVisitedProductTable",indices = [Index(value = ["productId"], unique = true)])
data class RecentlyVisitedModel(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    val id : Int = 0,
    val productName: String? = "",
    val productCoverImg: String? ="",
    @ColumnInfo(name = "productId")
    val productId: String = "",
    val productPrice: String? = "",
    val discountRate: String? = "",
    val onSale: Boolean? = false,
    val productSpecialPrice: String? = ""
)