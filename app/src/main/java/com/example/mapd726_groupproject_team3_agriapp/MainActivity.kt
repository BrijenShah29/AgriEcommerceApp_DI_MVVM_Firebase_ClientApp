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
import androidx.activity.viewModels
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
import androidx.lifecycle.Observer
import androidx.navigation.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.mapd726_groupproject_team3_agriapp.Fragments.OtherFragments.SearchFragment
import com.example.mapd726_groupproject_team3_agriapp.Fragments.ProductsFragment
import com.example.mapd726_groupproject_team3_agriapp.Utils.Constant
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.ViewModel.ProductsViewModel
import com.example.mapd726_groupproject_team3_agriapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var userName : String
    private lateinit var userNumber : String
    var userImage : String? = ""

    @Inject
    lateinit var userManager: UserManager

    val viewModel by viewModels<ProductsViewModel>()
    var totalQuantity = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()
        val appbar = binding.appBar
        setSupportActionBar(appbar)

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


        // GETTING CART TOTAL QUANTITY

        viewModel.cartProducts.observe(this, Observer {
            var i = 0
            for(items in it){
                i += items.productQuantity!!
            }
            totalQuantity = i

            invalidateOptionsMenu()
        })

        // SETTING UP CUSTOM ACTION BAR
//        binding.searchButton.setOnClickListener {
//            Toast.makeText(this, "Icon clicked", Toast.LENGTH_SHORT).show()
//            //navigateToSearchFragment()
//          // val currentFragment = navController.currentDestination
//           // val fragmentManager = supportFragmentManager
//
//           navController.navigate(R.id.searchFragment)
//        }
//        binding.cartButton.setOnClickListener {
//            //navController.popBackStack() // WILL BE CHANGED IN RELEASE 2
//            navController.navigate(R.id.cartFragmentNavigated)
//        }


        // SETTING UP SIDE DRAWER




        //setupActionBarWithNavController(navController)


         toggle = getActionBarDrawerToggle(binding.mainDrawer, binding.appBar)
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
                    R.id.categoryFragment -> "Category"
                    R.id.wishlistFragment -> "My Wishlist"
                    R.id.moreFragment -> "My Profile"
                    R.id.cartFragmentNavigated -> "My Cart"
                    R.id.searchFragment->"Search"
                    R.id.subCategoryFragment -> userManager.getSelectedCategory()
                    R.id.productsFragment -> userManager.getSelectedSubCategory()
                    R.id.checkoutFragment -> "Checkout"
                    else -> "Agro Mart"
                }
                when (destination.id)
                {
                    R.id.cartFragment -> {

                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true
                       // binding.appBarTitle.text = "Shopping Cart"
                        //appbar.menu[0].isVisible = false
                        //appbar.menu[1].isVisible = false
                        //invalidateOptionsMenu()
                      //  binding.appBarTitle.visibility = View.VISIBLE

                     //   binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)


                    }
                    R.id.moreFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true
                       // binding.appBarTitle.visibility = View.GONE
                        appbar.menu[0].isVisible = true
                        appbar.menu[1].isVisible = true

                    //    binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)



                    }
                    R.id.categoryFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true

                       // binding.appBarTitle.text = "Category"
                        //binding.appBarTitle.visibility = View.VISIBLE
                     //   binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)



                    }
                    R.id.wishlistFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true

                       // binding.appBarTitle.text = "Wishlist"
                       // binding.appBarTitle.visibility = View.VISIBLE

                     //   binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)


                    }
                    R.id.homeFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                        toggle.isDrawerIndicatorEnabled = true

                       // binding.appBarTitle.visibility = View.GONE

                     //   binding.appBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)



                    }
                    R.id.subCategoryFragment -> {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        //binding.appBarTitle.visibility = View.GONE
                        binding.appBar.setNavigationIcon(R.drawable.back_button)


                    }
                    R.id.searchFragment ->
                    {
                        binding.bottomBar.visibility = View.INVISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        binding.appBar.setNavigationIcon(R.drawable.back_button)
                       // binding.appBarTitle.text = "Search"
                       // binding.appBarTitle.visibility = View.VISIBLE
                        appbar.menu[0].isVisible = false
                        appbar.menu[1].isVisible = false


                    }
                    R.id.productsFragment ->
                    {
                        binding.bottomBar.visibility = View.INVISIBLE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        binding.appBar.setNavigationIcon(R.drawable.back_button)
                       // binding.appBarTitle.text = "Products"
                        //binding.appBarTitle.visibility = View.VISIBLE

                    }
                    R.id.detailedProductFragment->
                    {
                        binding.bottomBar.visibility = View.GONE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                      //  binding.appBarTitle.visibility = View.INVISIBLE
                        binding.appBar.setNavigationIcon(R.drawable.back_button)

                    }
                    R.id.cartFragmentNavigated->
                    {
                        binding.bottomBar.visibility = View.GONE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        binding.appBar.setNavigationIcon(R.drawable.back_button)
                        //binding.appBarTitle.text = "Shopping Cart"
                        //binding.appBarTitle.visibility = View.VISIBLE
                        //appbar.menu[0].isVisible = false
                        //appbar.menu[1].isVisible = false
                    }
                    else -> {
                        binding.bottomBar.visibility = View.GONE
                        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                        toggle.isDrawerIndicatorEnabled = false
                        appbar.menu[0].isVisible = false
                        appbar.menu[1].isVisible = false
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
            headerNumber.visibility = View.INVISIBLE
            headerImage.visibility = View.INVISIBLE
        }
        else
        {
            headerNumber.visibility = View.VISIBLE
            headerNumber.text = userNumber
            headerImage.visibility = View.VISIBLE
            if(userImage?.isNotBlank() == true && userImage?.isNotEmpty() == true && userImage!=""){
                userImage = userManager?.getUserProfileImage().toString()
                Glide.with(this).load(userImage).centerCrop().into(headerImage)
            }
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
        val menuItem = menu?.findItem(R.id.cart_appBar)

        // SETTING UP THE BADGE NOTIFICATION
        val actionView = menuItem?.actionView
        val cartBadgeTextView = actionView?.findViewById<TextView>(R.id.cart_badge_text)
        cartBadgeTextView?.text = totalQuantity.toString()
        cartBadgeTextView?.visibility = if(totalQuantity == 0) View.GONE else View.VISIBLE
        actionView?.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        when (item.itemId) {
            R.id.search_appBar -> {
                navController.navigate(R.id.searchFragment)
            }
            R.id.cart_appBar -> {
                navController.navigate(R.id.cartFragmentNavigated)
            }
            else -> {
                Toast.makeText(applicationContext, "Something Went Wrong!!", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
       val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()


        when(item.itemId)
        {
            R.id.search_appBar->
            {
                navController.navigate(R.id.searchFragment)
            }
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




