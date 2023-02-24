package com.example.mapd726_groupproject_team3_agriapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Switch
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mapd726_groupproject_team3_agriapp.databinding.ActivityMainBinding
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottom_navigation)
        binding.bottomBar.setupWithNavController(popupMenu.menu,navController)

        // SETTING UP CUSTOM ACTION BAR

        binding.searchButton.setOnClickListener {
            Toast.makeText(this, "Icon clicked", Toast.LENGTH_SHORT).show()
        }


        // SETTING UP SIDE DRAWER


        //setupActionBarWithNavController(navController)


        val toggle = getActionBarDrawerToggle(binding.mainDrawer, binding.appBar)
        binding.sideDrawer.setNavigationItemSelectedListener(this)
       // binding.appBar.setOnMenuItemClickListener {}

        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                title = when(destination.id){
                    R.id.cartFragment -> "My Cart"
                    R.id.categoryFragment -> "My Category"
                    R.id.wishlistFragment -> "My Wishlist"
                    R.id.moreFragment -> "My Profile"
                    R.id.subCategoryFragment->"My Category"
                    else -> "Home"
                }
                when (destination.id)
                {
                    R.id.cartFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true
                     //   binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)


                    }
                    R.id.moreFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true
                    //    binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)



                    }
                    R.id.categoryFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true
                     //   binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)



                    }
                    R.id.wishlistFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true
                     //   binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)


                    }
                    R.id.homeFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true
                     //   binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)



                    }
                    R.id.subCategoryFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        binding.appBar.setNavigationIcon(R.drawable.back_button)

                    }
                    else -> {
                        binding.bottomBar.visibility = View.GONE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        binding.appBar.setNavigationIcon(R.drawable.back_button)


                       // setActionBar()
                       // supportActionBar?.setDisplayHomeAsUpEnabled(true)
                       // supportActionBar?.setDisplayShowHomeEnabled(true)

                       // actionBarDrawerToggle.setToolbarNavigationClickListener {
                        //    onBackPressed()
                        }




                    }

                }

        })

        toggle.setToolbarNavigationClickListener {
            onBackPressed()
            //navController.navigateUp()

        }


    }


    fun getActionBarDrawerToggle(
        drawerLayout: DrawerLayout, toolbar: androidx.appcompat.widget.Toolbar): ActionBarDrawerToggle {
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        return toggle
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }


        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.myAccount_drawer ->
            {
                Log.d("Side Drawer Clicked","My Account clicked ")
                Toast.makeText(this, "My Account", Toast.LENGTH_SHORT).show()
            }
            R.id.vegetableSeeds_drawer->
            {
                Log.d("Side Drawer Clicked","Vegetable Seeds Clicked")
                Toast.makeText(this, "Vegetable Seeds", Toast.LENGTH_SHORT).show()
            }
            R.id.cropNutrition_drawer->
            {
                Log.d("Side Drawer Clicked","Crop Nutrition Clicked")
                Toast.makeText(this, "Crop Nutrition", Toast.LENGTH_SHORT).show()
            }
            R.id.cropProtection_drawer->
            {
                Log.d("Side Drawer Clicked","Crop Protection Clicked")
                Toast.makeText(this, "Crop Protection", Toast.LENGTH_SHORT).show()
            }
            R.id.wishlistDrawer->
            {
                Log.d("Side Drawer Clicked","Wishlist Clicked")
                Toast.makeText(this, "Wishlist", Toast.LENGTH_SHORT).show()
            }
            R.id.contactUs_drawer->
            {
             Log.d("Side Drawer Clicked","Contact Us Clicked")
             Toast.makeText(this, "Contact us", Toast.LENGTH_SHORT).show()
            }
            R.id.aboutUs_drawer->
            {
             Log.d("Side Drawer Clicked","About us Clicked")
             Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show()
            }
        }
        binding.mainDrawer.closeDrawer(GravityCompat.START)

       return true
    }

    override fun onBackPressed() {
        if(binding.mainDrawer.isDrawerOpen(GravityCompat.START)){
            binding.mainDrawer.closeDrawer(GravityCompat.START)
        }else
        {
        super.onBackPressed()
         }

   }

}




