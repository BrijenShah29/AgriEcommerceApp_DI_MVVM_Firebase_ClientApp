package com.example.mapd726_groupproject_team3_agriapp.loginFragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.LineBackgroundSpan
import android.text.style.StrikethroughSpan
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.mapd726_groupproject_team3_agriapp.MainActivity
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant.Companion.USER_NAME
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.UserViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentOtpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.FragmentComponentManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class OtpFragment : Fragment() {


lateinit var binding : FragmentOtpBinding
@Inject
lateinit var userManager : UserManager
private lateinit var verificationCode : String
    private lateinit var userNumber : String
    private lateinit var resendToken : PhoneAuthProvider.ForceResendingToken
    lateinit var auth : FirebaseAuth
    val TAG = "OTP FRAGMENT"

    val viewModel by viewModels<UserViewModel>()

    private lateinit var mProgressBar : ProgressBar

   // lateinit var  builder : AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()


        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)




        verificationCode = requireArguments().getString("verificationID").toString()
        userNumber = requireArguments().getString("number").toString()
        resendToken = requireArguments().getParcelable("resendToken")!!


        // SETTING UP PHONE NUMBER TEXT FIELD
        binding.phoneNumber.text = userNumber
        var spanString = SpannableString(binding.phoneNumber.text.toString())
        spanString.setSpan(UnderlineSpan(),0,binding.phoneNumber.text.lastIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.phoneNumber.text = spanString

        // SETTING UP PROGRESS BAR

        mProgressBar = binding.otpProgressBar
        mProgressBar.visibility = View.INVISIBLE
//         builder = AlertDialog.Builder(requireContext())
//            .setTitle("Loading")
//            .setMessage("Please Wait")
//            .setCancelable(false)


        // HIDING OTP RESENT FUNCTIONALITY FOR 60 SECONDS AT START
        getResendOTPVisibility()

        // SETTING ONCLICK FOR CHANGE OF NUMBER
        binding.phoneNumber.setOnClickListener{
            requireActivity().onBackPressed()
        }

        // SETTING UP ENTERING OTP



        // SETTING UP VERIFY BUTTON
        binding.continueButton.setOnClickListener {

            if(binding.otpInputTxt.text!!.isNotEmpty())
            {
                if(binding.otpInputTxt.text!!.length == 6){


                    mProgressBar.visibility = View.VISIBLE
                    val credential = PhoneAuthProvider.getCredential(verificationCode,binding.otpInputTxt.text.toString())
                    signInWithPhoneAuthCredential(credential)

                }
                else
                {
                    Snackbar.make(requireView(),"Please Enter Correct OTP",Snackbar.LENGTH_SHORT).show()
                }
            }else
            {
                 Snackbar.make(requireView(),"Please Enter OTP",Snackbar.LENGTH_SHORT).show()
            }
        }


        // SETTING UP RESEND OTP
        binding.resendOtpButton.setOnClickListener {
           mProgressBar.visibility = View.VISIBLE
            getResendOTPVisibility()
            resendVerificationCode()


        }

        return binding.root
    }

    private fun getResendOTPVisibility() {

        binding.otpInputTxt.text = null
        binding.resendOtpButton.visibility = View.GONE

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            binding.resendOtpButton.visibility = View.VISIBLE
        },60000)
    }

    private fun resendVerificationCode()  {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+$userNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            Log.d(TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            //  Log.w(TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            mProgressBar.visibility = View.INVISIBLE
            Snackbar.make(requireView(),"OTP Sent Successfully",Snackbar.LENGTH_SHORT).show()
            verificationCode = verificationId
            resendToken = token




        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("OTP FRAGMENT", "signInWithCredential:success")

                    val user = task.result?.user
                    sendToMainScreen(user)
                    //
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("OTP FRAGMENT", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    private fun sendToMainScreen(user: FirebaseUser?) {

        userManager.savePhoneNumber(user?.phoneNumber.toString()) //STORING NUMBER TO SHARED PREFERENCES
        // CHECK IF USER ALREADY EXISTS IN FIREBASE

            viewModel.getUSerDataFromFirebase("Users",userManager.getUserNumber().toString())
            viewModel.firebaseUserData.observe(viewLifecycleOwner, Observer {
                Handler(Looper.myLooper()!!).postDelayed(Runnable {
                    if(it!=null) {
                        if (it.customerId.isNotEmpty() || it.customerId.isNotBlank()) {


                            // Store UserData into Room db for future use
                            viewModel.insertUserDataIntoRoom(it)

                            //userManager.savePhoneNumber(it.customerNumber)
                            userManager.saveUserName(it.customerFirstName)
                            userManager.saveCustomerImage(it.customerImage)
                            userManager.saveCustomerId(it.customerId)

                            mProgressBar.visibility = View.INVISIBLE
                            // Login User into App
                            val intent = Intent(activity, MainActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        }
                    }
                    else
                    {
                        mProgressBar.visibility = View.INVISIBLE
                        //IF USER DOES NOT EXIST GO TO REGISTRATION PAGE
                        Navigation.findNavController(requireView())
                            .navigate(R.id.action_otpFragment_to_registrationFragment)

                    }

                },4000)


            })

//        val intent = Intent(activity, MainActivity::class.java)
//        startActivity(intent)
//        activity?.finish()
    }

}