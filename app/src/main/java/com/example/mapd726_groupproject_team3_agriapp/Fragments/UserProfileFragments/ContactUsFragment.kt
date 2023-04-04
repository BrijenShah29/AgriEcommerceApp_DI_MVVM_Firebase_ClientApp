package com.example.mapd726_groupproject_team3_agriapp.Fragments.UserProfileFragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentContactUsBinding

class ContactUsFragment : Fragment() {

    lateinit var binding : FragmentContactUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactUsBinding.inflate(layoutInflater)


        binding.contactUs.setOnClickListener {

            if(binding.editName.text.isNullOrEmpty()){
                binding.editName.error = " Please provide your name "
            }else if(binding.editPhone.text.isNullOrEmpty()){
                binding.editPhone.error = "Please provide your number"
            }
            else if(binding.editMessage.text.isNullOrEmpty()){
                binding.editMessage.error = "Please enter your message"
            }
            else
            {
                val name = binding.editName.text.toString()
                val number = binding.editPhone.text.toString()
                val message = binding.editMessage.text.toString()

                val sendMessageData = "Inquiry  person name : $name \n contact number :$number \n Inquiry Message : $message"

                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL,"info@Agromart.com")
                    putExtra(Intent.EXTRA_SUBJECT, "Customer Inquiry : $name , $number")
                    putExtra(Intent.EXTRA_TEXT,sendMessageData)
                }
                if(intent.resolveActivity(requireActivity().packageManager)!=null )
                {
                    startActivity(intent)
                }else
                {
                    Toast.makeText(requireContext(), "Required app is  not installed", Toast.LENGTH_SHORT).show()
                }

            }

        }


        return binding.root
    }


}