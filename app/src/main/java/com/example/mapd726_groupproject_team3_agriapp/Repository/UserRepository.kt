package com.example.mapd726_groupproject_team3_agriapp.Repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CategoryModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CustomerModel
import com.example.mapd726_groupproject_team3_agriapp.RoomDB.AgroDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class UserRepository @Inject constructor(val auth : FirebaseAuth, val db : FirebaseFirestore, val storage : FirebaseStorage, val agroDao: AgroDao)
{


    // SAVING USER DATA TO FIREBASE
    fun saveUserDataToFirebase(collectionPath: String,userNumber : String,data : CustomerModel): String {
        var status = " "
        db.collection(collectionPath).document(userNumber).set(data).addOnSuccessListener {
            status = "Success"
        }
            .addOnFailureListener{
                status = "Failed"
            }
        return status
    }


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