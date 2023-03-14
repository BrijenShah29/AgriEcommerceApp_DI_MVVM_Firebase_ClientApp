package com.example.mapd726_groupproject_team3_agriapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.databinding.ActivityLoginBinding
import com.example.mapd726_groupproject_team3_agriapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MAPD726_GroupProject_Team3_AgriApp)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.loginContainer)
        val navController = navHostFragment?.findNavController()

        navController?.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                when(destination.id){

                    R.id.loginFragment ->
                    {

                    }
                    R.id.otpFragment ->
                    {

                    }

                    else -> {

                    }
                }
            }

        } )

    }
}