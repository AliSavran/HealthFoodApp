package com.alisavran.healthfood.Roomdb

import android.adservices.adid.AdId
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alisavran.healthfood.Model.Food

@Dao
interface FoodDao {

    @Insert
    suspend fun insertAll(vararg food: Food): List<Long>

    @Query("SELECT * FROM food")
    suspend fun getAllFood() : List<Food>

    @Query("SELECT * FROM food WHERE uuid = :foodId")
    suspend fun getFood(foodId: Int): Food?

    @Query("DELETE FROM food")
    suspend fun deleteALL()
}