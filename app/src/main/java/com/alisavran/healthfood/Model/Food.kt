package com.alisavran.healthfood.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Food(
    @ColumnInfo(name = "isim")
    @SerializedName("isim")
    val foodName : String?,

    @ColumnInfo(name = "kalori")
    @SerializedName("kalori")
    val calorie : String?,

    @ColumnInfo(name = "karbonhidrat")
    @SerializedName("karbonhidrat")
    val carbohydrate : String?,

    @ColumnInfo(name = "protein")
    @SerializedName("protein")
    val protein : String?,

    @ColumnInfo(name = "yağ")
    @SerializedName("yağ")
    val oil : String?,

    @ColumnInfo(name = "gorsel")
    @SerializedName("gorsel")
    val image : String?

){
   @PrimaryKey(autoGenerate = true)
   var uuid :Int = 0
}