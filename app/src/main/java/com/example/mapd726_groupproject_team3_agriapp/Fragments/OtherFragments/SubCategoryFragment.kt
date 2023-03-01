package com.example.mapd726_groupproject_team3_agriapp.Fragments.OtherFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CategoryModel
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentSubCategoryBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SubCategoryFragment : Fragment() {

    private lateinit var binding : FragmentSubCategoryBinding
    private var list = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSubCategoryBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Picasso.get().load(requireArguments().getString("img")).resize(0,200 ).centerCrop().into(binding.imageView)
        Glide.with(requireContext()).load(requireArguments()
            .getString("img"))
            .centerCrop()
            .into(binding.imageView)
        getCategoriesByRootCategoryFromFirebase(requireArguments().getString("cat")!!)


        binding.listItem.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(requireContext(), "list on click"+list[i], Toast.LENGTH_SHORT).show()
            Log.d("on click recorded",list[i].toString())
          //  val intent = Intent(requireContext(), ProductsActivity :: class.java)
            //intent.putExtra("products",list[i].toString())
            //this.startActivity(intent)
            navigateToProductFragment(i)



        }






        return binding.root
    }

    private fun navigateToProductFragment(i: Int) {

        val bundle = Bundle()
        bundle.putString("products",list[i].toString())
        bundle.putString("field","productCategory")

        val sender = (requireContext() as FragmentActivity).supportFragmentManager
        sender.setFragmentResult("productSubCategory",bundle)

        Navigation.findNavController(binding.root)
            .navigate(R.id.action_subCategoryFragment_to_productsFragment,bundle)
    }

    private fun getCategoriesByRootCategoryFromFirebase(rootCategory: Any) {

        Firebase.firestore.collection("Categories").whereEqualTo("Root",rootCategory)
            .get().addOnSuccessListener {
                list?.clear()
                for(doc in it.documents){
                    val firebaseData = doc.toObject(CategoryModel::class.java)
                    list?.add(firebaseData!!.Category!!)
                    //Log.d("Fetched category", firebaseData.Category.toString())
                }
                    binding.listItem.adapter =
                        ArrayAdapter(requireContext(), R.layout.item_text_list_layout, list)

            }

    }


}