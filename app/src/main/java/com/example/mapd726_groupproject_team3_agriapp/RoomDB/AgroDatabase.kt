package com.example.mapd726_groupproject_team3_agriapp.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mapd726_groupproject_team3_agriapp.DataModels.*
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.TypeConvertor.TypeConvertors


@Database(entities = [ProductModel::class,CartModel::class, SubCategoryModel::class, RecentlyVisitedModel::class, CustomerModel::class], version = 1, exportSchema = false)
@TypeConverters(TypeConvertors::class)
abstract class AgroDatabase:RoomDatabase() {

    abstract fun agroDao() :AgroDao


}