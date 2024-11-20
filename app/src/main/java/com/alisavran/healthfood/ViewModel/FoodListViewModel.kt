package com.alisavran.healthfood.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alisavran.healthfood.Model.Food
import com.alisavran.healthfood.Roomdb.FoodDatabase
import com.alisavran.healthfood.Service.FoodApiService
import com.alisavran.healthfood.Util.FoodSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodListViewModel(application: Application) : AndroidViewModel(application) {

    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val foodLoading = MutableLiveData<Boolean>()

    private val foodApiService = FoodApiService()
    private val foodSharedPreferences = FoodSharedPreferences(application)

    private val updateTime = 0.1 * 60 * 1000 * 1000 * 1000L

    fun refreshData() {
        val savedTime = FoodSharedPreferences.takeTime()
        if (savedTime != null && System.nanoTime() - savedTime < updateTime) {
            takeDatabaseRoom()
        } else {
            getDataFromInternet()
        }
    }
    fun refreshDataFromInternet() {
        getDataFromInternet()
    }

    private fun takeDatabaseRoom() {
        foodLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val foodList = FoodDatabase(getApplication()).foodDao().getAllFood()
            withContext(Dispatchers.Main) {
                foodShow(foodList)
            }
        }
    }

    private fun getDataFromInternet() {
        foodLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val foodList = foodApiService.getData()
                withContext(Dispatchers.Main) {
                    foodLoading.value = false
                    saveRoom(foodList)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    foodErrorMessage.value = true
                    foodLoading.value = false
                }
            }
        }
    }

    private fun foodShow(foodList: List<Food>) {
        foods.value = foodList
        foodErrorMessage.value = false
        foodLoading.value = false
    }

    private fun saveRoom(foodList: List<Food>) {
        viewModelScope.launch(Dispatchers.IO) {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteALL()
            val uuidList = dao.insertAll(*foodList.toTypedArray())
            for (i in foodList.indices) {
                foodList[i].uuid = uuidList[i].toInt()
            }
            withContext(Dispatchers.Main) {
                foodShow(foodList)
            }
        }
        FoodSharedPreferences.saveTime(System.nanoTime())
    }
}
