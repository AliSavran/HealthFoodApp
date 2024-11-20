package com.alisavran.healthfood.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.alisavran.healthfood.Util.doPlaceHolder
import com.alisavran.healthfood.Util.downloadImage
import com.alisavran.healthfood.ViewModel.FoodDetailViewModel
import com.alisavran.healthfood.ViewModel.FoodListViewModel
import com.alisavran.healthfood.databinding.FragmentFoodDetailBinding

class FoodDetailFragment : Fragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FoodDetailViewModel
    private var foodId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            foodId = FoodDetailFragmentArgs.fromBundle(it).foodId
        }
        viewModel = ViewModelProvider(this)[FoodDetailViewModel::class.java]
        viewModel.roomBase(foodId)

        observeLiveData()
    }


    private fun observeLiveData(){
        viewModel.foodLiveData.observe(viewLifecycleOwner) { food ->
            binding.foodName.text = food.foodName
            binding.foodCalorie.text = food.calorie
            binding.foodCarbohydrate.text = food.carbohydrate
            binding.foodProtein.text = food.protein
            binding.foodOil.text = food.oil
            binding.foodImage.downloadImage(food.image, doPlaceHolder(requireContext()))
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}