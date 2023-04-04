package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mapd726_groupproject_team3_agriapp.Activities.LoginActivity
import com.example.mapd726_groupproject_team3_agriapp.Adapter.RecentlyVisitedProductsAdapter.RecentlyVisitedAdapter
import com.example.mapd726_groupproject_team3_agriapp.MainActivity
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.HomeViewModel
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.UserViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentMoreBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MoreFragment : Fragment() {

    lateinit var binding : FragmentMoreBinding

    private val viewModel by viewModels<UserViewModel>()
    private val viewModelProducts by viewModels<ProductsViewModel>()

    private var coverImage : Uri? = null
    private var coverImageUrl : String? = ""

    @Inject
    lateinit var userManager : UserManager

    private lateinit var userName : String


    private var launchGalleryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            coverImage = it.data!!.data
            binding.userImage.setImageURI(coverImage)
            binding.userImage.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(layoutInflater)

        //SETTING UP RECENTLY VISITED PRODUCT RECYCLERVIEW
        val adapter = RecentlyVisitedAdapter(requireContext(),viewModelProducts)
        binding.productLastVisited.adapter = adapter
        binding.productLastVisited.setHasFixedSize(true)
        viewModelProducts.recentlyVisitedProducts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        userManager = UserManager(requireContext())

        userName = userManager.getUserName().toString()
        binding.userImage.isEnabled = false

        // SETTING UP GUEST VIEW FOR WISHLIST
        if(userName == Constant.USER_GUEST)
        {
            binding.guestUserViewLayout.visibility = View.VISIBLE
            binding.signedInUserViewLayout.visibility = View.GONE
            // Redirect to Sign in page when On click
        }
        else
        {
            binding.guestUserViewLayout.visibility = View.GONE
            binding.signedInUserViewLayout.visibility = View.VISIBLE

            binding.userName.text = userManager.getUserName().toString()
            binding.userNumber.text = userManager.getUserNumber().toString()
            binding.userImage.isEnabled = true

            Glide.with(this).load(userManager.getUserProfileImage().toString()).apply(RequestOptions.centerCropTransform()).into(binding.userImage)


        }

        // SIGN-OUT BUTTON ON CLICK FUNCTIONALITY

        binding.LogOutButton.setOnClickListener {
            val alert = AlertDialog.Builder(requireContext()).setMessage("Are you sure about Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes"){dialog,id ->
                    viewModel.signOut()
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()

                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }

            alert.create().show()


        }
        // GUEST USER SIGN IN ON CLICK
        binding.signInbutton.setOnClickListener {
            viewModel.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.userNameNumberLayout.setOnClickListener {
            binding.editUserFirstName.visibility = VISIBLE
            binding.editUserLastName.visibility = VISIBLE
            binding.editUserNumber.visibility = GONE
            binding.userName.visibility = GONE
            binding.userNumber.visibility = GONE
            binding.userImage.isClickable = true
            binding.userImage.isEnabled = true
            binding.saveChanges.visibility = VISIBLE

        }

        binding.saveChanges.setOnClickListener {
            userManager.savePhoneNumber(binding.editUserNumber.text.toString())
            userManager.saveUserName(binding.editUserFirstName.text.toString())

           // userManager.saveCustomerImage(binding.)

            binding.saveChanges.visibility = GONE
            binding.editUserFirstName.visibility = GONE
            binding.editUserLastName.visibility = GONE
            binding.editUserNumber.visibility = GONE
            binding.userName.visibility = VISIBLE
            binding.userNumber.visibility = VISIBLE
            binding.userName.setText( userManager.getUserName().toString() + binding.editUserLastName.text)
            binding.userNumber.text = userManager.getUserNumber()
            binding.userImage.isEnabled = false

           // uploadProfileImage to Storage
            if(coverImage != null) {
                val fileName = userManager.getUserNumber().toString() + ".jpg"

                val refStorage = FirebaseStorage.getInstance().reference.child("Users/$fileName")
                refStorage.putFile(coverImage!!)
                    .addOnSuccessListener {
                        it.storage.downloadUrl.addOnSuccessListener { image ->
                            coverImageUrl = image.toString()
                        }
                            .addOnFailureListener {
                                Log.d("failed", "failed to upload the profile")
                                Toast.makeText(requireContext(),
                                    "Something Went Wrong While Uploading your Image",
                                    Toast.LENGTH_SHORT).show()
                            }
                    }
            }

            // upload Retrieved profile image url and other data
            CoroutineScope(Dispatchers.IO).launch {
                delay(3000)
                viewModel.updateUserProfileInfo(binding.editUserFirstName.text.toString(),binding.editUserLastName.text.toString(),coverImageUrl,userManager.getUserNumber()!!)
            }

        }

        binding.userImage.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            launchGalleryActivity.launch(intent)
        }

        binding.YourOrdersButton.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_ordersFragment)
        }

        binding.ManageAddressesButton.setOnClickListener {
            Toast.makeText(requireContext(), "Redirecting Manage Addresses", Toast.LENGTH_SHORT).show()
        }

        binding.contactUsButton.setOnClickListener {
            Toast.makeText(requireContext(), "Redirecting to Contact us page", Toast.LENGTH_SHORT).show()
        }

        binding.aboutUsButton.setOnClickListener {
            Toast.makeText(requireContext(), "Redirecting to about us page", Toast.LENGTH_SHORT).show()
        }

        binding.rateUsButton.setOnClickListener {
            Toast.makeText(requireContext(), "We're still in development Phase..", Toast.LENGTH_SHORT).show()
        }
        binding.faq.setOnClickListener {
            Toast.makeText(requireContext(), "We're still in development Phase..", Toast.LENGTH_SHORT).show()
        }
        binding.tnc.setOnClickListener {
            Toast.makeText(requireContext(), "We're still in development Phase..", Toast.LENGTH_SHORT).show()
        }
        binding.privacyPolicy.setOnClickListener {
            Toast.makeText(requireContext(), "We're still in development Phase..", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun uploadProfileImageToFirebase() {
        if(coverImage != null)
        {
            val fileName = userManager.getUserNumber().toString()+".jpg"

            val refStorage = FirebaseStorage.getInstance().reference.child("Users/$fileName")

            refStorage.putFile(coverImage!!)
                .addOnSuccessListener {
                    it.storage.downloadUrl.addOnSuccessListener { image ->
                        coverImageUrl = image.toString()
                        userManager.saveCustomerImage(coverImageUrl)

                    }
                        .addOnFailureListener {
                            Log.d("failed","failed to upload the profile")
                            Toast.makeText(requireContext(), "Something Went Wrong While Uploading your Image", Toast.LENGTH_SHORT).show()
                        }
                }
        }
}

    override fun onDestroyView() {
        super.onDestroyView()
        //uploadProfileDataToFirebase()
    }

    private fun uploadProfileDataToFirebase() {

    }

}