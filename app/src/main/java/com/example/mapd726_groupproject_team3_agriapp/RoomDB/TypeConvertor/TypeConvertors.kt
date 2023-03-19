package com.example.mapd726_groupproject_team3_agriapp.RoomDB.TypeConvertor

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
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
    fun toDate(dateLong: Timestamp?): Date? {
        return Date(dateLong!!.time)
    }

    @TypeConverter
    fun fromDate(date: Date?): Timestamp? {
        return Timestamp(date?.time!!)
    }


    @TypeConverter
    fun toDate(stringdate: String?): Date? {
        return stringdate?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): String? {
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return dateTimeFormat.format(date)
    }

//    @TypeConverter
//    fun fromTimestamp(value: Long?): Date? {
//        return if (value == null) null else Date(value)
//    }

}