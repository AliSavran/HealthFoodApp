package com.alisavran.healthfood.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alisavran.healthfood.Model.Food
import com.alisavran.healthfood.Roomdb.FoodDao
import com.alisavran.healthfood.Roomdb.FoodDatabase
import com.alisavran.healthfood.Util.FoodSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class FoodDetailViewModel(application: Application) : AndroidViewModel(application) {

    val foodLiveData = MutableLiveData<Food>()
    fun roomBase(uuid: Int){
        viewModelScope.launch {
            val dao =FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData.value = food
        }
    }
}