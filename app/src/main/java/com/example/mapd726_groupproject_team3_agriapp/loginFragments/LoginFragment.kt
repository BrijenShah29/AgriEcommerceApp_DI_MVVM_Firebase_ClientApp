package com.example.mapd726_groupproject_team3_agriapp.loginFragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.example.mapd726_groupproject_team3_agriapp.MainActivity
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.FragmentComponentManager
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var userManager: UserManager
    private var userNumber : String? = ""
    lateinit var auth : FirebaseAuth

    private lateinit var mProgressBar : ProgressBar

 //   lateinit var  builder : AlertDialog.Builder

    val TAG = "FIREBASE AUTH"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
       binding =  FragmentLoginBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()

        mProgressBar = binding.otpProgressBar
        mProgressBar.visibility = View.INVISIBLE

        //SETTING UP GUEST LOGIN
        binding.guestUserButton.setOnClickListener{

            userManager.saveUserName("Guest")
            userManager.savePhoneNumber(Constant.USER_NUMBER)

            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        // SETTING UP USER LOGIN

        val number = binding.ccp
        number.registerCarrierNumberEditText(binding.phoneInputTxt)

        binding.continueButton.setOnClickListener {

            if (Pattern.matches("^(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}\$", number.fullNumber))
            {
                mProgressBar.visibility = View.VISIBLE
                userNumber = number.fullNumber
                Log.d("phoneNumber",userNumber.toString())
               verifyUserAuth(userNumber.toString())


            }
            else
            {
                Snackbar.make(requireView(),"Enter a Valid Number",Snackbar.LENGTH_SHORT).show()
            }
        }

        return binding.root

    }

    private fun verifyUserAuth(fullNumber: String?) {
//  showing progress bar
//        builder = AlertDialog.Builder(requireContext())
//            .setTitle("Loading")
//            .setMessage("Please Wait")
//            .setCancelable(false)
//            builder.show()


        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+$fullNumber")
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
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
            mProgressBar.visibility = View.VISIBLE

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
//            builder.show().dismiss()
            val bundle = Bundle()
            bundle.putString("verificationID",verificationId)
            bundle.putParcelable("resendToken", token)
            bundle.putString("number",userNumber.toString())

            mProgressBar.visibility = View.INVISIBLE

            val sender = (FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager
            sender.setFragmentResult("fromLoginPage",bundle)

            Navigation.findNavController(requireView())
                .navigate(R.id.action_loginFragment_to_otpFragment,bundle)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    sendToMainScreen(user)
                   //
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
                mProgressBar.visibility = View.INVISIBLE
            }
    }

    private fun sendToMainScreen(user: FirebaseUser?) {
        userManager.savePhoneNumber(user?.phoneNumber.toString()) //STORING NUMBER TO SHARED PREFERENCES
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser!=null)
        {
            userManager.saveUserName(Constant.REGISTERED_USER)
            userManager.savePhoneNumber(auth.currentUser?.phoneNumber.toString()) //STORING NUMBER TO SHARED PREFERENCES
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }


}