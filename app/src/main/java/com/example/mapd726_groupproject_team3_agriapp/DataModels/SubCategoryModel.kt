package com.example.mapd726_groupproject_team3_agriapp.DataModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "subCategoryTable")
class SubCategoryModel (
    @PrimaryKey
    @NotNull
    var categoryId: String="",
    var root : String? = "",
    var SubCategory : String? = "",
    var Category : String? = "",
    var Image : String? =""
)