package com.example.mapd726_groupproject_team3_agriapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mapd726_groupproject_team3_agriapp.Fragments.HomeFragment
import com.example.mapd726_groupproject_team3_agriapp.Fragments.OtherFragments.SearchFragment
import com.example.mapd726_groupproject_team3_agriapp.databinding.ActivityMainBinding
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
            //navigateToSearchFragment()
          // val currentFragment = navController.currentDestination
           // val fragmentManager = supportFragmentManager



           navController.navigate(R.id.searchFragment)
        }
        binding.cartButton.setOnClickListener {
            //navController.popBackStack() // WILL BE CHANGED IN RELEASE 2
            navController.navigate(R.id.cartFragment)
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
                arguments: Bundle?,
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
                        binding.appBarTitle.text = "Shopping Cart"
                        binding.appBarTitle.visibility = View.VISIBLE
                        binding.searchButton.visibility = View.GONE
                        binding.cartButton.visibility = View.GONE

                     //   binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)


                    }
                    R.id.moreFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true
                        binding.appBarTitle.visibility = View.GONE
                        binding.searchButton.visibility = View.VISIBLE
                        binding.cartButton.visibility = View.VISIBLE
                    //    binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)



                    }
                    R.id.categoryFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true
                        binding.appBarTitle.visibility = View.GONE
                        binding.searchButton.visibility = View.VISIBLE
                        binding.cartButton.visibility = View.VISIBLE
                     //   binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)



                    }
                    R.id.wishlistFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true
                        binding.appBarTitle.text = "Wishlist"
                        binding.appBarTitle.visibility = View.VISIBLE
                        binding.searchButton.visibility = View.VISIBLE
                        binding.cartButton.visibility = View.VISIBLE

                     //   binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)


                    }
                    R.id.homeFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true
                        binding.appBarTitle.visibility = View.GONE
                        binding.searchButton.visibility = View.VISIBLE
                        binding.cartButton.visibility = View.VISIBLE

                     //   binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)



                    }
                    R.id.subCategoryFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        binding.appBarTitle.visibility = View.GONE
                        binding.appBar.setNavigationIcon(R.drawable.back_button)
                        binding.searchButton.visibility = View.VISIBLE
                        binding.cartButton.visibility = View.VISIBLE

                    }
                    R.id.searchFragment ->
                    {
                        binding.bottomBar.visibility = View.INVISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        binding.appBar.setNavigationIcon(R.drawable.back_button)
                        binding.appBarTitle.text = "Search"
                        binding.appBarTitle.visibility = View.VISIBLE
                        binding.searchButton.visibility = View.GONE
                        binding.cartButton.visibility = View.GONE

                    }
                    R.id.productsFragment ->
                    {
                        binding.bottomBar.visibility = View.INVISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        binding.appBar.setNavigationIcon(R.drawable.back_button)
                        binding.appBarTitle.text = "Products"
                        binding.appBarTitle.visibility = View.VISIBLE
                        binding.searchButton.visibility = View.VISIBLE
                        binding.cartButton.visibility = View.VISIBLE
                    }
                    R.id.detailedProductFragment->
                    {
                        binding.bottomBar.visibility = View.GONE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        binding.searchButton.visibility =View.VISIBLE
                        binding.cartButton.visibility = View.VISIBLE
                        binding.appBar.setNavigationIcon(R.drawable.back_button)
                    }
                    else -> {
                        binding.bottomBar.visibility = View.GONE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        binding.searchButton.visibility =View.GONE
                        binding.cartButton.visibility = View.GONE
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
        drawerLayout: DrawerLayout, toolbar: androidx.appcompat.widget.Toolbar,
    ): ActionBarDrawerToggle {
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

    private fun navigateToSearchFragment() {
        val searchFragment = SearchFragment()
        show(R.id.homeFragment, searchFragment)
    }

    private fun show(oldFragment: Int, newFragment: Fragment) {
        //val drawerLayout = drawer_layout as DrawerLayout

         // .beginTransaction().remove(this).replace(com.example.mapd726_groupproject_team3_agriapp.R.id.fragmentContainer, newFrame).addToBackStack(null).commit()
        //drawerLayout.closeDrawer(GravityCompat.START)
    }

}




