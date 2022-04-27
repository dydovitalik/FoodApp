package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.models.Hours
import com.example.foodapp.models.RestaurantModel
import java.text.SimpleDateFormat
import java.util.*

class RestaurantListAdapter(val restaurantList: List<RestaurantModel?>?): RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantListAdapter.MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_restaurant_list_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantListAdapter.MyViewHolder, position: Int) {
        holder.bind(restaurantList?.get(position))
    }

    override fun getItemCount(): Int {
        return restaurantList?.size!!
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        val thumbImage: ImageView = view.findViewById(R.id.thumbImage)
        val tvRestaurantName: TextView = view.findViewById(R.id.tvRestaurantName)
        val tvRestaurantAddress: TextView = view.findViewById(R.id.tvRestaurantAddress)
        val tvRestaurantHours: TextView = view.findViewById(R.id.tvRestaurantHours)

        fun bind(restaurantModel: RestaurantModel?){

        Glide.with(thumbImage).load(restaurantModel?.image).into(thumbImage)
        tvRestaurantName.text = restaurantModel?.name
        tvRestaurantAddress.text = "Address: " +restaurantModel?.address
        tvRestaurantHours.text = "Today's Hours: " +getTodaysHours(restaurantModel?.hours!!)
        }
    }

    private fun getTodaysHours(hours: Hours): String? {
        val calendar: Calendar = Calendar.getInstance()
        val date: Date = calendar.time
        val day: String = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
        return when(day) {
            "Sunday" -> hours.Sunday
            "Monday" -> hours.Monday
            "Tuesday" -> hours.Tuesday
            "Wednesday" -> hours.Wednesday
            "Friday" -> hours.Friday
            "Saturday" -> hours.Saturday
            else -> hours.Sunday
        }
    }


}