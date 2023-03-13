package com.example.mapd726_groupproject_team3_agriapp.Utils

import android.content.Context
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_NAME
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_NAME_FILE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_NUMBER
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_NUMBER_FILE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserManager @Inject constructor(@ApplicationContext context: Context)
{
    private var prefsName = context.getSharedPreferences(USER_NAME_FILE,Context.MODE_PRIVATE)
    private var prefsNumber = context.getSharedPreferences(USER_NUMBER_FILE,Context.MODE_PRIVATE)

    fun saveUserName(username : String){
        val editor = prefsName.edit()
        editor.putString(USER_NAME,username)
        editor.apply()
    }

    fun savePhoneNumber(phoneNumber : String){
        val editor = prefsNumber.edit()
        editor.putString(USER_NUMBER,phoneNumber)
        editor.apply()
    }


    fun getUserName() : String? {
        return prefsName.getString(USER_NAME,"GUEST_USER")
    }

    fun getUserNumber() : String? {
        return prefsNumber.getString(USER_NUMBER,null)
    }
















}