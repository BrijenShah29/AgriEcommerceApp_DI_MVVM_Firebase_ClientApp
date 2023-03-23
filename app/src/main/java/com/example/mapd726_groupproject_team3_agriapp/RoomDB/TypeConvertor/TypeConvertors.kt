package com.example.mapd726_groupproject_team3_agriapp.RoomDB.TypeConvertor

import androidx.room.TypeConverter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.OrderedProductsModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.lang.reflect.Type
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class TypeConvertors {

    @TypeConverter
    fun fromString(value: String?): ArrayList<String?>? {
        val listType = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromDateToString(value: Date): String {
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return dateTimeFormat.format(value)
    }

    @TypeConverter
    fun toDateFromString(value: String) : Date {
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return dateTimeFormat.parse(value)
    }

    @TypeConverter
    fun fromListToString(value : List<String>) : String{
        return value.joinToString("#")
    }
    @TypeConverter
    fun fromStringToList(value : String) : List<String>{
        return value.split("#")

    }

    @TypeConverter
    fun stringToListServer(data: String?): List<OrderedProductsModel>{
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            com.google.gson.reflect.TypeToken<List<OrderedProductsModel?>?>() {}.type
        return Gson().fromJson<List<OrderedProductsModel>>(data, listType)
    }

    @TypeConverter
    fun listServerToString(someObjects: List<OrderedProductsModel>): String? {
        return Gson().toJson(someObjects)
    }

}