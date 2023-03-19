package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.mapd726_groupproject_team3_agriapp.Adapter.CategoryFragmentAdapters.RootCategoryAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CategoryModel
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentCategoryBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding

    @Inject
    lateinit var userManager: UserManager
    private var rootCategoryList = ArrayList<CategoryModel>()
    private var subCategoryList = ArrayList<CategoryModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCategoryBinding.inflate(layoutInflater)

        // to resolve lateinit var error only
        userManager = UserManager(requireContext())
        getRootCategoriesFromFirebase()








        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getRootCategoriesFromFirebase() {
        var i = 0
        Firebase.firestore.collection("Categories").get().addOnSuccessListener {
            rootCategoryList.clear()
            for (doc in it.documents) {
                val firebaseData = doc.toObject(CategoryModel::class.java)
                rootCategoryList.add(firebaseData!!)
                rootCategoryList.sortBy {
                    it.Image
                }
            }
            subCategoryList = rootCategoryList

            while (i < rootCategoryList.size) {
                if (rootCategoryList[i].Root.toString() == "Seeds" || rootCategoryList[i].Root.toString() == "Crop Protection" || rootCategoryList[i].Root.toString() == "Crop Nutrition") {
                    rootCategoryList.removeAt(i)
                    rootCategoryList.sortBy {
                        it.Image
                    }
                }

                i += 1
            }
                binding.categoryRecycler.adapter =
                    RootCategoryAdapter(requireContext(), rootCategoryList,userManager)
        }

    }
}
