package com.example.mapd726_groupproject_team3_agriapp.loginFragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CustomerModel
import com.example.mapd726_groupproject_team3_agriapp.MainActivity
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.UserViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentRegistrationBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    lateinit var binding : FragmentRegistrationBinding
     val viewModel by viewModels<UserViewModel>()
    var userNumber : String? = null
    var userFirstName : String? = null
    var userLastName : String? = null
    var userEmail : String? = null
    var userImage : String? = null
    private lateinit var dialog: Dialog
    private var coverImage : Uri? = null
    private var coverImageUrl : String? = ""

        @Inject
    lateinit var userManager : UserManager


    private var launchGalleryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            coverImage = it.data!!.data
            binding.updateUserProfileImage.setImageURI(coverImage)
            binding.updateUserProfileImage.visibility = View.VISIBLE
            binding.updateUserProfileImage.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(layoutInflater)

        dialog = Dialog(requireContext())
        dialog.setCancelable(false)
        dialog.setTitle("Please Wait While We Setup a New Profile For You ")
        dialog.setContentView(R.layout.progress_layout)

        userManager = UserManager(requireContext())

        binding.cellNumber.isEnabled = false
        binding.cellNumber.isClickable = false
        userNumber = userManager.getUserNumber()
        binding.cellNumber.setText(userNumber.toString())

        binding.updateUserProfileImage.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            launchGalleryActivity.launch(intent)
        }


        binding.registerButton.setOnClickListener {

            if (!Pattern.matches("[a-zA-Z]+", binding.firstName.text.toString())) {
                binding.firstName.error = "Please Enter a Valid Name"
            }
            else if (!Pattern.matches("[a-zA-Z]+", binding.lastName.text.toString())) {
                binding.lastName.error = "Please Enter a Valid Name"
            }
            else if(!Pattern.matches("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"+"\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+",binding.emailId.text.toString()))
                {
                binding.emailId.error = "Please Enter A Valid Email"
            }
            else
            {
                userFirstName = binding.firstName.text.toString()
                userLastName = binding.lastName.text.toString()
                userEmail =binding.emailId.text.toString()
                dialog.show()
                setupUserProfile()

            }

        }

        binding.continueButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }



        return binding.root
    }

    private fun setupUserProfile() {
        uploadProfileImageToFirebase()

    }

    private fun uploadProfileImageToFirebase() {
        if(coverImage != null)
        {
            val fileName = userNumber.toString()+".jpg"

            val refStorage = FirebaseStorage.getInstance().reference.child("Users/$fileName")

            refStorage.putFile(coverImage!!)
                .addOnSuccessListener {
                    it.storage.downloadUrl.addOnSuccessListener { image ->
                        coverImageUrl = image.toString()
                        uploadProfileDataToFirebase()

                    }
                        .addOnFailureListener {
                            dialog.dismiss()
                             Log.d("failed","failed to upload the profile")
                            Toast.makeText(requireContext(), "Something Went Wrong While Uploading your Image", Toast.LENGTH_SHORT).show()
                        }
                }
        }
        else
        {
            uploadProfileDataToFirebase()
        }
    }


    private fun uploadProfileDataToFirebase() {

        var db = Firebase.firestore.collection("Users")
        val key = db.document().id
        val data = CustomerModel(
            key,
            userNumber,
            userFirstName,
            userLastName,
            userEmail,
            "",
            coverImageUrl
        )
        CoroutineScope(Dispatchers.Main).launch{
            val status = viewModel.saveUserDataToFirebase("Users",userNumber.toString(),data)
            delay(2500)
            if(status)
            {
                showContinueProcess(key)

            }
            else
            {
                Log.d("failed","failed to upload the profile")
                Toast.makeText(requireContext(), "Something Went wrong While Creating Profile", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun showContinueProcess(key: String) {

        binding.firstNameLayoutCard.visibility = View.GONE
        binding.lastNameLayoutCard.visibility =View.GONE
        binding.emailLayoutCard.visibility = View.GONE
        binding.numberLayoutCard.visibility = View.GONE
        binding.updateUserProfileImage.visibility = View.GONE
        binding.registerButton.visibility = View.GONE
        binding.textView4.visibility = View.GONE
        binding.successMessage.visibility = View.VISIBLE
        binding.continueButton.visibility = View.VISIBLE

        binding.successMessage.text = R.string.thankyou.toString()+userFirstName.toString()+R.string.confirmRegister.toString()
        setupUserProfileForApp(key)
        dialog.dismiss()
    }

    private fun setupUserProfileForApp(key: String) {
        userManager.saveCustomerImage(coverImageUrl)
        userManager.saveUserName(userFirstName)
        userManager.saveCustomerId(key)
        userManager.savePhoneNumber(userNumber)
    }


}