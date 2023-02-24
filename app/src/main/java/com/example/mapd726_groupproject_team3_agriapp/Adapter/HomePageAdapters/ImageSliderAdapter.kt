package com.example.mapd726_groupproject_team3_agriapp.Adapter.HomePageAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.example.mapd726_groupproject_team3_agriapp.DataModels.Slider
import com.example.mapd726_groupproject_team3_agriapp.R
import com.smarteist.autoimageslider.SliderViewAdapter


class ImageSliderAdapter(private val context: Context, private val slider: ArrayList<Slider>) : SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH>() {

    private var mSliderItems = slider

    fun renewItems(sliderItems: ArrayList<Slider>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems!!.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem:Slider) {
        mSliderItems!!.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_item, parent,false)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem = mSliderItems!![position]
        Glide.with(context)
            .load(sliderItem.img).override(400)
            .fitCenter()
            .into(viewHolder.imageViewBackground)

        viewHolder.itemView.setOnClickListener {
            Toast.makeText(context,
                "This is item in position $position",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems!!.size
    }

    inner class SliderAdapterVH(itemView: View) :ViewHolder(itemView) {
        var imageViewBackground: ImageView
      //  var imageGifContainer: ImageView
       // var textViewDescription: TextView

        init {
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider)
          //  imageGifContainer = itemView.findViewById(R.id.iv_gif_container)
          //  textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider)
        }
    }
}