package com.example.mapd726_groupproject_team3_agriapp.DataModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "CustomerDataTable")
data class CustomerModel(
    @PrimaryKey
    @NotNull
    val customerId : String= "",
    val customerNumber : String? ="",
    val customerFirstName : String? ="",
    val customerLastName : String? = "",
    val customerEmail: String? ="",
    val customerPassword : String? ="",
    val customerImage : String? = "",
    val streetAddress : String? = "",
    val city : String? ="",
    val province : String? = "",
    val zipCode : String? = "",
    val wishList : ArrayList<String> = ArrayList()
)
