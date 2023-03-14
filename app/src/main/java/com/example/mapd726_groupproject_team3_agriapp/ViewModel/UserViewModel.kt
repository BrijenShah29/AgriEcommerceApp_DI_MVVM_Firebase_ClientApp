package com.example.mapd726_groupproject_team3_agriapp.ViewModel

import androidx.lifecycle.ViewModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CustomerModel
import com.example.mapd726_groupproject_team3_agriapp.Repository.FirebaseRepository
import com.example.mapd726_groupproject_team3_agriapp.Repository.ProductRepository
import com.example.mapd726_groupproject_team3_agriapp.Repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val firebaseRepository: FirebaseRepository, val productRepository: ProductRepository, val userRepository: UserRepository) : ViewModel()
{

    // USER DATA STORE TO FIRE STORE

    fun saveUserDataToFirebase(collectionPath: String, userNumber: String, data: CustomerModel): String {
        return userRepository.saveUserDataToFirebase(collectionPath, userNumber, data)
    }




    // USER SIGN OUT
    fun signOut() {
        userRepository.signOut()
    }



}