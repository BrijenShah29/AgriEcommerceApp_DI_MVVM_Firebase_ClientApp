package com.example.mapd726_groupproject_team3_agriapp.Utils

import android.content.Context
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.PREFERRED_CATEGORY
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.PREFERRED_CATEGORY_FILE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.SELECTED_CATEGORY
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.SELECTED_CATEGORY_FILE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.SELECTED_SUB_CATEGORY
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.SELECTED_SUB_CATEGORY_FILE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_CITY
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_CITY_FILE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_GUEST
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_ID
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_ID_FILE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_IMAGE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_IMAGE_FILE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_NAME
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_NAME_FILE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_NUMBER
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_NUMBER_FILE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_PROVINCE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_PROVINCE_FILE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_STREET_ADDRESS
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_STREET_ADDRESS_FILE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_ZIPCODE
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_ZIPCODE_FILE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserManager @Inject constructor(@ApplicationContext context: Context)
{
    private var prefsName = context.getSharedPreferences(USER_NAME_FILE,Context.MODE_PRIVATE)
    private var prefsNumber = context.getSharedPreferences(USER_NUMBER_FILE,Context.MODE_PRIVATE)
    private var prefsSubCategory = context.getSharedPreferences(SELECTED_SUB_CATEGORY_FILE,Context.MODE_PRIVATE)
    private var prefsCategory = context.getSharedPreferences(SELECTED_CATEGORY_FILE,Context.MODE_PRIVATE)
    private var prefsUserImage = context.getSharedPreferences(USER_IMAGE_FILE,Context.MODE_PRIVATE)
    private var prefsUserId = context.getSharedPreferences(USER_ID_FILE,Context.MODE_PRIVATE)
    private var prefsUserStreetAddress = context.getSharedPreferences(USER_STREET_ADDRESS_FILE,Context.MODE_PRIVATE)
    private var prefsUserZipCode = context.getSharedPreferences(USER_ZIPCODE_FILE,Context.MODE_PRIVATE)
    private var prefsUserProvince = context.getSharedPreferences(USER_PROVINCE_FILE,Context.MODE_PRIVATE)
    private var prefsUserCity = context.getSharedPreferences(USER_CITY_FILE,Context.MODE_PRIVATE)
    private var prefPreferredCategory = context.getSharedPreferences(PREFERRED_CATEGORY_FILE,Context.MODE_PRIVATE)


    fun saveUserName(username : String?){
        val editor = prefsName.edit()
        editor.putString(USER_NAME,username)
        editor.apply()
    }

    fun savePhoneNumber(phoneNumber : String?){
        val editor = prefsNumber.edit()
        editor.putString(USER_NUMBER,phoneNumber)
        editor.apply()
    }

    fun saveSelectedUserCategory(selectedCategory: String?){
        val editor = prefsCategory.edit()
        editor.putString(SELECTED_CATEGORY,selectedCategory)
        editor.apply()
    }

    fun saveSelectedUserSubCategory(selectedSubCategory : String?){
        val editor = prefsSubCategory.edit()
        editor.putString(SELECTED_SUB_CATEGORY,selectedSubCategory)
        editor.apply()
    }

    fun saveCustomerImage(customerImage : String?){
        val editor = prefsUserImage.edit()
        editor.putString(USER_IMAGE,customerImage)
        editor.apply()
    }

    fun saveCustomerId(customerId : String?){
        val editor = prefsUserId.edit()
        editor.putString(USER_ID,customerId)
        editor.apply()
    }

    fun savePreferredStreetAddress(address : String?){
        val editor = prefsUserStreetAddress.edit()
        editor.putString(USER_STREET_ADDRESS,address)
        editor.apply()
    }

    fun savePreferredProvince(province : String?){
        val editor = prefsUserProvince.edit()
        editor.putString(USER_PROVINCE,province)
        editor.apply()
    }

    fun savePreferredZipcode(zipcode : String?){
        val editor =prefsUserZipCode.edit()
        editor.putString(USER_ZIPCODE,zipcode)
        editor.apply()
    }

    fun savePreferredCity(city : String?){
        val editor =prefsUserCity.edit()
        editor.putString(USER_CITY,city)
        editor.apply()
    }

    fun savePreferredCategory(prefCategory : String?){
        val editor =prefPreferredCategory.edit()
        editor.putString(PREFERRED_CATEGORY,prefCategory)
        editor.apply()
    }





    fun getUserName() : String? {
        return prefsName.getString(USER_NAME, USER_GUEST)
    }

    fun getUserNumber() : String? {
        return prefsNumber.getString(USER_NUMBER,null)
    }


   fun getSelectedCategory() : String? {
       return prefsCategory.getString(SELECTED_CATEGORY,"Category")
   }

  fun getSelectedSubCategory() : String? {
        return prefsSubCategory.getString(SELECTED_SUB_CATEGORY,"Sub_Category")
    }

  fun getUserProfileImage() : String? {
        return prefsUserImage.getString(USER_IMAGE,null)
    }

  fun getUserId() : String? {
        return prefsUserId.getString(USER_ID,null)
    }

    fun getPreferredUserAddress() : String?
    {
        return prefsUserStreetAddress.getString(USER_STREET_ADDRESS,null)
    }

    fun getPreferredUserZipcode() : String?
    {
        return prefsUserZipCode.getString(USER_ZIPCODE,null)
    }

    fun getPreferredUserProvince() : String?
    {
        return prefsUserProvince.getString(USER_PROVINCE,null)
    }

    fun getPreferredUserCity() : String?
    {
        return prefsUserCity.getString(USER_CITY,null)
    }

    fun getPreferredCategory() : String?
    {
        return prefPreferredCategory.getString(PREFERRED_CATEGORY,"ALL")
    }




















}