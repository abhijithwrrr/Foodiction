package com.internshala.foodrunner.adapter

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.internshala.foodrunner.R
import com.internshala.foodrunner.activity.model.Restaurants
import com.internshala.higherorderfunctionalitiessolution.database.RestaurantDatabase
import com.internshala.higherorderfunctionalitiessolution.database.RestaurantEntity
import com.squareup.picasso.Picasso

class AllRestaurantsAdapter(private var restaurants: ArrayList<Restaurants>, val context: Context) :
    RecyclerView.Adapter<AllRestaurantsAdapter.AllRestaurantsViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AllRestaurantsViewHolder {
        val itemView = LayoutInflater.from(p0.context)
            .inflate(R.layout.fragment_home, p0, false)

        return AllRestaurantsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onBindViewHolder(p0: AllRestaurantsViewHolder, p1: Int) {
        val resObject = restaurants.get(p1)

        p0.restaurantName.text = resObject.name
        p0.rating.text = resObject.rating
        val costForTwo = "${resObject.cost.toString()}/person"
        p0.cost.text = costForTwo
        Picasso.get().load(resObject.imageUrl).error(R.drawable.foodiction).into(p0.resThumbnail)


        val listOfFavourites = GetAllFavAsyncTask(context).execute().get()

        if (listOfFavourites.isNotEmpty() && listOfFavourites.contains(resObject.id.toString())) {
            p0.favImage.setImageResource(R.drawable.ic_favorite)
        } else {
            p0.favImage.setImageResource(R.drawable.ic_favorite)
        }

        p0.favImage.setOnClickListener {
            val restaurantEntity = RestaurantEntity(
                resObject.id,
                resObject.name,
                resObject.rating,
                resObject.cost.toString(),
                resObject.imageUrl
            )

            if (!DBAsyncTask(context, restaurantEntity, 1).execute().get()) {
                val async =
                    DBAsyncTask(context, restaurantEntity, 2).execute()
                val result = async.get()
                if (result) {
                    p0.favImage.setImageResource(R.drawable.ic_favorite)
                }
            } else {
                val async = DBAsyncTask(context, restaurantEntity, 3).execute()
                val result = async.get()

                if (result) {
                    p0.favImage.setImageResource(R.drawable.ic_favorite)
                }
            }
        }

        p0.cardRestaurant.setOnClickListener {
            Toast.makeText(context, "Clicked on: ${p0.restaurantName.text}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    class AllRestaurantsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resThumbnail = view.findViewById(R.id.imgRes) as ImageView
        val restaurantName = view.findViewById(R.id.txtRestaurantName) as TextView
        val rating = view.findViewById(R.id.txtResRating) as TextView
        val cost = view.findViewById(R.id.txtCost) as TextView
        val cardRestaurant = view.findViewById(R.id.cardRestaurant) as CardView
        val favImage = view.findViewById(R.id.imgFav) as ImageView
    }

    class DBAsyncTask(context: Context, val restaurantEntity: RestaurantEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {


            when (mode) {

                1 -> {
                    val res: RestaurantEntity? =
                        db.restaurantDao().getRestaurantById(restaurantEntity.id.toString())
                    db.close()
                    return res != null
                }

                2 -> {
                    db.restaurantDao().insertRestaurant(restaurantEntity)
                    db.close()
                    return true
                }

                3 -> {
                    db.restaurantDao().deleteRestaurant(restaurantEntity)
                    db.close()
                    return true
                }
            }

            return false
        }

    }


    class GetAllFavAsyncTask(
        context: Context
    ) :
        AsyncTask<Void, Void, List<String>>() {

        val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "res-db").build()
        override fun doInBackground(vararg params: Void?): List<String> {

            val list = db.restaurantDao().getAllRestaurants()
            val listOfIds = arrayListOf<String>()
            for (i in list) {
                listOfIds.add(i.id.toString())
            }
            return listOfIds
        }
    }
}