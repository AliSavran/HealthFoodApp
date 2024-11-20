package com.alisavran.healthfood.Util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class FoodSharedPreferences(context: Context) {

    companion object {
        private const val TIME = "time"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: FoodSharedPreferences? = null
        private val lock = Any()

        operator fun invoke(context: Context): FoodSharedPreferences {
            return instance ?: synchronized(lock) {
                instance ?: createFoodSharedPreferences(context).also {
                    instance = it
                }
            }
        }

        private fun createFoodSharedPreferences(context: Context): FoodSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return FoodSharedPreferences(context)
        }

        fun saveTime(nanoTime: Long) {
            sharedPreferences?.edit()?.putLong(TIME, nanoTime)?.apply()
        }

        fun takeTime(): Long {
            return sharedPreferences?.getLong(TIME, 0) ?: 0
        }
    }
}
