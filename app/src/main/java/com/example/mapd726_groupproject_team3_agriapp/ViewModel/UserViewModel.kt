package com.example.mapd726_groupproject_team3_agriapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CustomerModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.OrderModel
import com.example.mapd726_groupproject_team3_agriapp.Repository.FirebaseRepository
import com.example.mapd726_groupproject_team3_agriapp.Repository.ProductRepository
import com.example.mapd726_groupproject_team3_agriapp.Repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  UserViewModel @Inject constructor(val firebaseRepository: FirebaseRepository, val productRepository: ProductRepository, val userRepository: UserRepository) : ViewModel()
{

    // USER DATA STORE TO FIRE STORE

    var status : Boolean? = null;
    suspend fun saveUserDataToFirebase(collectionPath: String, userNumber: String, data: CustomerModel): Boolean {

        viewModelScope.launch(Dispatchers.IO) {
            status = userRepository.saveUserDataToFirebase(collectionPath, userNumber, data)
         }
        delay(2000)
         return status!!
    }

    // GETTING USER DATA FROM FIREBASE

    private var _firebaseUserData = MutableLiveData<CustomerModel>()
    val firebaseUserData : LiveData<CustomerModel>
        get() = _firebaseUserData

    fun getUSerDataFromFirebase(collectionPath: String,userNumber: String) {
        viewModelScope.async {
            _firebaseUserData.value = userRepository.getUserDataFromFirebase(collectionPath,userNumber).value
        }
    }


    // SAVING FETCHED USER INFO FROM FIREBASE TO ROOM DB
    fun insertUserDataIntoRoom(customerModel: CustomerModel){
        viewModelScope.launch {
            userRepository.insertUserDataIntoRoom(customerModel)
        }

    }

    //TO GET STORED USER DATA FROM DATABASE

    private var _storedUserDataFromRoom = MutableLiveData<CustomerModel>()
    val storedUserDataFromRoom : LiveData<CustomerModel>
        get() = _storedUserDataFromRoom

    fun getUserData(customerId: String) {
        viewModelScope.launch {
            _storedUserDataFromRoom.value = userRepository.getUserDataFromRoom(customerId)
        }

    }

    // USER SIGN OUT
    fun signOut() {
        userRepository.signOut()
    }

    // STORING USER ORDER AFTER SUCCESSFUL ORDER PAYMENT

    fun addOrdersIntoRoomDB(orderModel: OrderModel){

        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertOrder(orderModel)
        }
    }

    // FETCHING USER ORDERS FROM FIREBASE

    private var _fetchUserOrders = MutableLiveData<ArrayList<OrderModel>>()
    val fetchUserOrders : LiveData<ArrayList<OrderModel>>
        get() = _fetchUserOrders
    fun fetchUserOrders(userId : String){
        viewModelScope.launch(Dispatchers.IO) {
            _fetchUserOrders.value = firebaseRepository.getOrdersForCurrentUsers(userId).value
        }
    }

    // Cancelling Order from user side if item is not shipped

    private var _cancelOrder = MutableLiveData<String>()
    val cancelOrder : LiveData<String>
        get()  = _cancelOrder

    fun cancelOrder(orderId:String){
        viewModelScope.launch(Dispatchers.IO) {
            _cancelOrder.value = firebaseRepository.cancelOrder(orderId).value
        }
    }

    fun updateUserProfileInfo(firstName: String, lastName: String, userProfile: String?,userNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUserProfileInfo(firstName, lastName, userProfile,userNumber)
        }


    }


}