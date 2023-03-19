package com.example.mapd726_groupproject_team3_agriapp.Repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CategoryModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CustomerModel
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(val auth : FirebaseAuth, val db : FirebaseFirestore, val storage : FirebaseStorage, val agroDao: AgroDao)
{


    // SAVING USER DATA TO FIREBASE
    suspend fun saveUserDataToFirebase(collectionPath: String,userNumber : String,data : CustomerModel): Boolean {
        var status : Boolean? = null
        db.collection(collectionPath).document(userNumber).set(data).addOnSuccessListener {
            status = true
        }
            .addOnFailureListener{
                status = false
            }
        delay(1500)
        return status!!
    }


    private var _firebaseData = MutableLiveData<CustomerModel>()
    val firebaseData : LiveData<CustomerModel>
        get() = _firebaseData

    //GETTING USER DATA FROM FIREBASE
    suspend fun getUserDataFromFirebase(collectionPath: String,userNumber: String):LiveData<CustomerModel>{


        db.collection(collectionPath).document(userNumber).get().addOnSuccessListener {
                _firebaseData.value = it.toObject(CustomerModel::class.java)
            }
        delay(2000)
        return firebaseData
    }

    // To Store Fetched User Data into RoomDB
    suspend fun insertUserDataIntoRoom(customerModel: CustomerModel) = agroDao.insertUserData(customerModel)

    // To get Stored User Data From RoomDB

    suspend fun getUserDataFromRoom(customerId: String) = agroDao.getUserData(customerId)

    //To delete UserData from Room Before Saving


    //GETTING USER DATA FROM FIREBASE, PARSING THEM AS LIVE DATA

    private val categories = MutableLiveData<ArrayList<CategoryModel>>()
    private val listCategories : LiveData<ArrayList<CategoryModel>>
        get() = categories

    fun getCategoriesFromFirebase(collectionPath: String, categoryList: ArrayList<CategoryModel>?): LiveData<ArrayList<CategoryModel>> {
        db.collection(collectionPath).get().addOnSuccessListener {
            categoryList?.clear()
            for (doc in it.documents) {
                val firebaseData = doc.toObject(CategoryModel::class.java)
                categoryList?.add(firebaseData!!)
                //Log.d("Fetched category", firebaseData.Category.toString())
            }
            categories.value = categoryList!!
        }
        return listCategories
    }

    // USER SIGNOUT
    fun signOut()
    {
        auth.signOut()
    }

}