package com.alisavran.healthfood.Service

import com.alisavran.healthfood.Model.Food
import retrofit2.http.GET

interface FoodAPI {
    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    //base url =https://raw.githubusercontent.com/
    //endpoint = atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    suspend fun getFood(): List<Food>
}