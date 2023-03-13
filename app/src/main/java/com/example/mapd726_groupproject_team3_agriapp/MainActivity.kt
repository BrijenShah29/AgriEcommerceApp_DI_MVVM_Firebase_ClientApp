package com.example.mapd726_groupproject_team3_agriapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.mapd726_groupproject_team3_agriapp.Fragments.OtherFragments.SearchFragment
import com.example.mapd726_groupproject_team3_agriapp.Fragments.ProductsFragment
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var userName : String
    lateinit var userNumber : String

    @Inject
    lateinit var userManager: UserManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottom_navigation)
        binding.bottomBar.setupWithNavController(popupMenu.menu,navController)

        //binding.sideDrawer.setupWithNavController(navController)

        //RESOLVING ISSUE OF NAVIGATION OF BOTTOM BAR

        binding.bottomBar.onItemSelected = {
            when(it){
                0 -> {
                    navController.popBackStack()
                }
//               1 -> {
//                    navController.navigate(R.id.categoryFragment)
//                }
//                2-> {
//                   navController.navigate(R.id.moreFragment)
//               }
//                3-> {
//                    navController.navigate(R.id.wishlistFragment)
//               }
//                4->{
//                    navController.navigate(R.id.cartFragment)
//                }

            }

        }


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
            navController.navigate(R.id.cartFragmentNavigated)
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
                        binding.appBarTitle.text = "Category"
                        binding.appBarTitle.visibility = View.VISIBLE
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
                        binding.appBarTitle.visibility = View.INVISIBLE
                        binding.searchButton.visibility =View.VISIBLE
                        binding.cartButton.visibility = View.VISIBLE
                        binding.appBar.setNavigationIcon(R.drawable.back_button)
                    }
                    R.id.cartFragmentNavigated->
                    {
                        binding.bottomBar.visibility = View.GONE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        binding.appBar.setNavigationIcon(R.drawable.back_button)
                        binding.appBarTitle.text = "Shopping Cart"
                        binding.appBarTitle.visibility = View.VISIBLE
                        binding.searchButton.visibility = View.GONE
                        binding.cartButton.visibility = View.GONE
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
            binding.sideDrawer.menu[0].isChecked = true
           navController.navigateUp()

        }

        // SETTING UP HEADER TITLE / NUMBER / USER IMAGE

        val header = binding.sideDrawer.getHeaderView(0)
        val headerTitle = header.findViewById<TextView>(R.id.header_user_name)
        val headerImage = header.findViewById<ImageView>(R.id.header_user_image)
        val headerNumber = header.findViewById<TextView>(R.id.header_user_number)


        // GETTING USER INFO FROM SHARED PREFERENCES

        userName = userManager.getUserName().toString()
        userNumber = userManager.getUserNumber().toString()
        headerTitle.text = userName
        if(userNumber == Constant.USER_NUMBER){
            headerNumber.visibility = View.GONE
            headerImage.visibility = View.GONE
        }
        else
        {
            headerNumber.visibility = View.VISIBLE
            headerNumber.text = userNumber
            headerImage.visibility = View.VISIBLE
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
       val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()


        when(item.itemId)
        {
            R.id.myAccount_drawer ->

            {
                //Navigation.findNavController(this,R.id.fragmentContainer).navigate(R.id.moreFragment)
                navController.navigate(R.id.moreFragment)
               // navController.navigate(R.id.moreFragment)
                Log.d("Side Drawer Clicked","My Account clicked ")
                Toast.makeText(this, "My Account", Toast.LENGTH_SHORT).show()

            }
            R.id.vegetableSeeds_drawer ->
            {
                val bundle = Bundle()
                bundle.putString("products","Vegetable Seeds")
                bundle.putString("field","productCategory")

                navController.navigate(R.id.productsFragment,bundle)

                Log.d("Side Drawer Clicked","Vegetable Seeds Clicked")
                Toast.makeText(this, "Vegetable Seeds", Toast.LENGTH_SHORT).show()
            }
            R.id.plantGrowth_drawer ->
            {
                val bundle = Bundle()
                bundle.putString("products","Plant Growth Promoters")
                bundle.putString("field","productCategory")

                navController.navigate(R.id.productsFragment,bundle)

                Log.d("Side Drawer Clicked","Crop Nutrition Clicked")
                Toast.makeText(this, "Crop Nutrition", Toast.LENGTH_SHORT).show()
            }
            R.id.fertilizers_drawer ->
            {
                val bundle = Bundle()
                bundle.putString("products","Fertilizers")
                bundle.putString("field","productCategory")

                navController.navigate(R.id.productsFragment,bundle)

                Log.d("Side Drawer Clicked","Crop Protection Clicked")
                Toast.makeText(this, "Crop Protection", Toast.LENGTH_SHORT).show()
            }
            R.id.potsNPlanters_drawer ->
            {
                val bundle = Bundle()
                bundle.putString("products","Pots and Planters")
                bundle.putString("field","productCategory")

               navController.navigate(R.id.productsFragment,bundle)

                Log.d("Side Drawer Clicked","Crop Nutrition Clicked")
                Toast.makeText(this, "Crop Nutrition", Toast.LENGTH_SHORT).show()
            }
            R.id.tools_drawer ->
            {
                val bundle = Bundle()
                bundle.putString("products","Tools")
                bundle.putString("field","productCategory")
                navController.navigate(R.id.productsFragment,bundle)

                Log.d("Side Drawer Clicked","Crop Nutrition Clicked")
                Toast.makeText(this, "Crop Nutrition", Toast.LENGTH_SHORT).show()
            }
            R.id.wishlistDrawer ->
            {

               navController.navigate(R.id.wishlistFragment)

                Log.d("Side Drawer Clicked","Wishlist Clicked")
                Toast.makeText(this, "Wishlist", Toast.LENGTH_SHORT).show()
            }
            R.id.contactUs_drawer ->
            {
              navController.navigate(R.id.contactUsFragment)

             Log.d("Side Drawer Clicked","Contact Us Clicked")
             Toast.makeText(this, "Contact us", Toast.LENGTH_SHORT).show()
            }
            R.id.aboutUs_drawer ->
            {
             navController.navigate(R.id.aboutUsFragment)

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
        //show(R.id.homeFragment, searchFragment)
    }

    private fun navigateToNewFragment(newFragment: Fragment, string : String) {
        val bundle = Bundle()
        bundle.putString("products",string)
        bundle.putString("field","productCategory")
      //  supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer).addToBackStack(null).commit()
        //val drawerLayout = drawer_layout as DrawerLayout

         // .beginTransaction().remove(this).replace(com.example.mapd726_groupproject_team3_agriapp.R.id.fragmentContainer, newFrame).addToBackStack(null).commit()
        //drawerLayout.closeDrawer(GravityCompat.START)
    }

}




