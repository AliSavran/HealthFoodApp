package com.alisavran.healthfood.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alisavran.healthfood.Adapter.FoodAdapter
import com.alisavran.healthfood.ViewModel.FoodListViewModel
import com.alisavran.healthfood.databinding.FragmentFoodListBinding

class FoodListFragment : Fragment() {
    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FoodListViewModel
    private val foodRecyclerAdapter = FoodAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FoodListViewModel::class.java]
        viewModel.refreshData()

        binding.foodRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.foodRecyclerView.adapter = foodRecyclerAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.foodRecyclerView.visibility = View.GONE
            binding.errorText.visibility = View.GONE
            binding.foodProgressBar.visibility = View.VISIBLE
            viewModel.refreshDataFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        observeLiveData()
    }

    private fun observeLiveData(){

        viewModel.foods.observe(viewLifecycleOwner){
            foodRecyclerAdapter.foodListUpdate(it)
            binding.foodRecyclerView.visibility = View.VISIBLE
        }

        viewModel.foodErrorMessage.observe(viewLifecycleOwner){
            if (it){
                binding.errorText.visibility= View.VISIBLE
                binding.foodRecyclerView.visibility = View.GONE
            }else {
                binding.errorText.visibility = View.GONE
            }
        }
        viewModel.foodLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.foodRecyclerView.visibility = View.GONE
                binding.errorText.visibility = View.GONE
                binding.foodProgressBar.visibility = View.VISIBLE
            } else {
                binding.foodProgressBar.visibility = View.GONE
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}