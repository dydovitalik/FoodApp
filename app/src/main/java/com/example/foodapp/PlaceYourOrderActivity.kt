package com.example.foodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.adapter.PlaceYourOrderAdapter
import com.example.foodapp.models.RestaurantModel
import kotlinx.android.synthetic.main.activity_place_your_order.*

class PlaceYourOrderActivity : AppCompatActivity() {

    var placeYourOrderAdapter: PlaceYourOrderAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_your_order)

        val restaurantModel: RestaurantModel? = intent.getParcelableExtra("RestaurantModel")
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(restaurantModel?.name)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        buttonPlaceYourOrder.setOnClickListener {
          onPlaceYourOrderClick(restaurantModel)
        }

        initRecyclerView(restaurantModel)
        calculateTotalAmount(restaurantModel)
    }

    private fun initRecyclerView(restaurantModel: RestaurantModel?){
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        placeYourOrderAdapter = PlaceYourOrderAdapter(restaurantModel?.menus)
        cartItemsRecyclerView.adapter = placeYourOrderAdapter
    }

    private fun calculateTotalAmount(restaurantModel: RestaurantModel?){
        var subTotalAmount = 0f
        for(menu in restaurantModel?.menus!!){
            subTotalAmount += menu?.price!! * menu?.totalInCart!!
        }
        tvSubtotalAmount.text = "$" + String.format("%.2f", subTotalAmount)

    }

    private fun onPlaceYourOrderClick(restaurantModel: RestaurantModel?){

       val intent = Intent(this@PlaceYourOrderActivity, SuccessOrderActivity::class.java)
       intent.putExtra("RestaurantModel", restaurantModel)
       startActivityForResult(intent,1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1000){
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}