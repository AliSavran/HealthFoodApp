package com.alisavran.healthfood.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alisavran.healthfood.Model.Food
import com.alisavran.healthfood.Util.doPlaceHolder
import com.alisavran.healthfood.Util.downloadImage
import com.alisavran.healthfood.View.FoodListFragmentDirections
import com.alisavran.healthfood.databinding.FoodRecyclerRowBinding
import com.alisavran.healthfood.databinding.FragmentFoodDetailBinding

class FoodAdapter(val foodList : ArrayList<Food>): RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(val binding: FoodRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = FoodRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun foodListUpdate(newFoodList : List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.binding.name.text = foodList[position].foodName
        holder.binding.calorie.text = foodList[position].calorie

        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(foodList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.imageView.downloadImage(foodList[position].image, doPlaceHolder(holder.itemView.context))
    }
}