package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CustomerModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.ProductModel
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.UserViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentShippingDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.FragmentComponentManager
import kotlinx.coroutines.*
import java.util.ArrayList
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class ShippingDetailsFragment : Fragment() {


    lateinit var binding : FragmentShippingDetailsBinding

    val viewModel by viewModels<UserViewModel>()
    private var userName : String? = null
    var userPreferredStreetAddress : String? = null
    var userPreferredZipCode : String? = null
    var userPreferredProvince : String? = null
    var userPreferredCity : String? = null
    var isDataFoundInServer : Boolean? = null
    var userId : String? = null
    private lateinit var dialog: Dialog
    private var status : Boolean ? = null


    @Inject
    lateinit var userManager : UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentShippingDetailsBinding.inflate(layoutInflater)

        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.progress_layout)
        dialog.setCancelable(false)

        userName = userManager.getUserName()
        userPreferredStreetAddress = userManager.getPreferredUserAddress()
        userPreferredZipCode = userManager.getPreferredUserZipcode()
        userPreferredProvince = userManager.getPreferredUserProvince()
        userPreferredCity = userManager.getPreferredUserCity()

        if(userName == Constant.USER_GUEST)
        {
            binding.signInButton.visibility = View.VISIBLE
            binding.guestTxt.visibility = View.VISIBLE
            binding.userNumber.isEnabled = true
            binding.notifNewsAndOfferCheckbox.visibility = View.GONE
        }
        else
        {
            binding.signInButton.visibility = View.GONE
            binding.guestTxt.visibility = View.GONE
            binding.userNumber.isEnabled = false
            viewModel.getUserData(userManager.getUserId().toString())
            binding.notifNewsAndOfferCheckbox.visibility = View.VISIBLE
            viewModel.storedUserDataFromRoom.observe(viewLifecycleOwner, Observer {
                dialog.show()

                Handler(Looper.myLooper()!!).postDelayed(Runnable {

                if(it!=null){

                    if(userPreferredStreetAddress.isNullOrEmpty() || userPreferredProvince.isNullOrEmpty() || userPreferredZipCode.isNullOrEmpty())
                    {
                        binding.firstName.setText(it.customerFirstName.toString())
                        binding.lastName.setText(it.customerLastName.toString())
                        binding.userNumber.setText(it.customerNumber.toString())
                        binding.numberLayout.isClickable = false
                        binding.emailAddressTxt.setText(it.customerEmail.toString())
                        binding.addressTxt.setText(it.streetAddress.toString())
                        binding.cityTxt.setText(it.city.toString())
                        binding.zipcode.setText(it.zipCode.toString())
                        binding.province.setText(it.province.toString())
                        dialog.dismiss()
                    }
                    else
                    {
                        binding.firstName.setText(it.customerFirstName.toString())
                        binding.lastName.setText(it.customerLastName.toString())
                        binding.userNumber.setText(it.customerNumber.toString())
                        binding.numberLayout.isClickable = false
                        binding.emailAddressTxt.setText(it.customerEmail.toString())
                        binding.cityTxt.setText(userPreferredCity.toString())
                        binding.addressTxt.setText(userPreferredStreetAddress.toString())
                        binding.zipcode.setText(userPreferredZipCode.toString())
                        binding.province.setText(userPreferredProvince.toString())
                        dialog.dismiss()
                    }

                    isDataFoundInServer = true
                    userId = it.customerId
                }
                else
                {
                    // No data found of user in Room or in Server
                    isDataFoundInServer = false
                    dialog.dismiss()
                    binding.firstName.setText(userName)
                    binding.userNumber.setText(userManager.getUserNumber())
                    userId = userManager.getUserId().toString()
                }
                },2000)
            })

            binding.ctnShippingButton.setOnClickListener {

                    validateUserInput()

            }



        }



        return binding.root
    }

    private fun storeUserPreferencesForAddress() {

        userManager.savePreferredStreetAddress(binding.addressTxt.text.toString())
        userManager.savePreferredProvince(binding.province.text.toString())
        userManager.savePreferredZipcode(binding.zipcode.text.toString())
        userManager.savePreferredCity(binding.cityTxt.text.toString())
    }

    private fun validateUserInput() {

        if (!Pattern.matches("[a-zA-Z]+", binding.firstName.text.toString()))
        {
            binding.firstName.error = "Please Enter a Valid Name"
        }
        else if (!Pattern.matches("[a-zA-Z]+", binding.lastName.text.toString()))
        {
            binding.lastName.error = "Please Enter a Valid Name"
        }
        else if(!Pattern.matches("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"+"\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+",binding.emailAddressTxt.text.toString()))
        {
            binding.emailAddressTxt.error = "Please Enter A Valid Email"
        }
        else if(!Pattern.matches("[-0-9A-Za-z.,/ ]+", binding.addressTxt.text.toString()))
        {
            binding.addressTxt.error = "Please enter a valid address"
        }
        else if (!Pattern.matches("[a-zA-Z]+", binding.cityTxt.text.toString()))
        {
            binding.lastName.error = "Please Enter a Valid City"
        }
        else if(binding.province.text.toString().length!=2)
        {
            binding.province.error = "Please Enter your Province"
        }
        else if(!Pattern.matches("[ABCEGHJKLMNPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z][ ]?\\d[ABCEGHJ-NPRSTV-Z]\\d" , binding.zipcode.text.toString()))
        {
            binding.zipcode.error = "Please Enter a Valid Zipcode"
        }
        else
        {
            val userData = CustomerModel(
                userId!!,
                binding.userNumber.text.toString(),
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.emailAddressTxt.text.toString(),
                "",
                userManager.getUserProfileImage().toString(),
                binding.addressTxt.text.toString(),
                binding.cityTxt.text.toString(),
                binding.province.text.toString(),
                binding.zipcode.text.toString()
            )


            if(binding.saveFutureShippingAddressCheckbox.isChecked)
                {
                    storeUserPreferencesForAddress()
                }

            if(binding.notifNewsAndOfferCheckbox.isChecked)
            {
                storeUserNumberToSendOffersAsText()
            }

            if(isDataFoundInServer == false)
            {

                //CHECKING IF USER IS NOT GUEST
                if(userName != Constant.USER_GUEST){

                val alert = AlertDialog.Builder(requireContext()).setMessage("Thank you for your First Order ! Would you like to store these details to your Profile ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialogInterface, id ->
                        dialog.show()

                        CoroutineScope(Dispatchers.IO).launch {
                            status = viewModel.saveUserDataToFirebase("Users",
                                userData)

                            delay(2500)
                            if (status == true) {
                                viewModel.insertUserDataIntoRoom(userData)
                                dialog.dismiss()
                                Snackbar.make(requireView(),
                                    "Profile Has been Updated successfully",
                                    Snackbar.LENGTH_SHORT).show()
                                    redirectUserToCheckoutPage()
                                    dialog.show()
                            } else {
                                Toast.makeText(requireContext(),
                                    "Something went wrong while updating your Profile..",
                                    Toast.LENGTH_SHORT).show()
                            }

                        }
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                        redirectUserToCheckoutPage()
                    }
                alert.create().show()
                }
            }
            else
            {
                if(userName == Constant.USER_GUEST){

                    val alert = AlertDialog.Builder(requireContext()).setMessage("Thank you for your Order ! Would you like to sign up first for great User Experience ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialogInterface, id ->
                            dialog.dismiss()

                            // Navigate user to Login Screen
                            // remove backtrack
                            Navigation.findNavController(binding.root).navigate(R.id.loginFragment)

                        }
                        .setNegativeButton("No") { Interface, id ->
                            // Dismiss the dialog
                            dialog.show()

                            CoroutineScope(Dispatchers.IO).launch {
                                status = viewModel.saveUserDataToFirebase("Users",
                                    userData)

                                delay(2000)
                                if (status == true) {
                                    viewModel.insertUserDataIntoRoom(userData)
                                    dialog.dismiss()
                                    Snackbar.make(requireView(),
                                        "Profile Has been Updated successfully",
                                        Snackbar.LENGTH_SHORT).show()
                                    redirectUserToCheckoutPage()
                                    dialog.show()
                                } else {
                                    Toast.makeText(requireContext(),
                                        "Something went wrong while updating your Profile..",
                                        Toast.LENGTH_SHORT).show()
                                }

                            }
                            dialog.dismiss()

                        }
                    alert.create().show()
                }
            }
            dialog.show()
            Handler(Looper.myLooper()!!).postDelayed(Runnable {
                redirectUserToCheckoutPage()
            },2500)

        }

}

    private fun redirectUserToCheckoutPage() {
        dialog.show()

        val cartList = requireArguments().getParcelableArrayList<CartModel>("CartProducts")
        val cartTotal = requireArguments().getDouble("CartTotal")
        val cartTotalTax = requireArguments().getDouble("cartTotalTax")
        val cartTotalPayable = requireArguments().getDouble("cartTotalPayable")
        val totalQuantity = requireArguments().getInt("totalQuantity")

        //NAVIGATING T0 CHECKOUT PAGE

        val bundle  = Bundle()
        bundle.putParcelableArrayList("CartProducts", cartList)
        bundle.putDouble("CartTotal",cartTotal)
        bundle.putDouble("cartTotalTax",cartTotalTax)
        bundle.putDouble("CartTotalPayable",cartTotalPayable)
        bundle.putInt("totalQuantity",totalQuantity)
        bundle.putString("shippingEmail",binding.emailAddressTxt.text.toString())
        bundle.putString("ShippingNumber",binding.userNumber.text.toString())
        bundle.putString("shippingCustomerName",String.format("%s %s",binding.firstName.text.toString(),binding.lastName.text.toString()))
        bundle.putString("ShippingAddress",String.format(" %s , %s , %s , %s , %s",binding.addressTxt.text.toString(),binding.cityTxt.text.toString(),binding.province.text.toString(),binding.zipcode.text.toString(),binding.countryName.text.toString()))


        val sender = (FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager
        //val sender = (requireContext() as FragmentActivity).supportFragmentManager
        sender.setFragmentResult("cartData",bundle)

        dialog.dismiss()

        Navigation.findNavController(binding.root).navigate(R.id.action_shippingDetailsFragment_to_checkoutFragment,bundle)
    }

    private fun storeUserNumberToSendOffersAsText() {

    }

}