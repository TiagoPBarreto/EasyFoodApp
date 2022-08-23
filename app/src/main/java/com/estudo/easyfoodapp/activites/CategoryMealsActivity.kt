package com.estudo.easyfoodapp.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.estudo.easyfoodapp.R
import com.estudo.easyfoodapp.adapters.CategoryMealsAdapter
import com.estudo.easyfoodapp.databinding.ActivityCategoryMealsBinding
import com.estudo.easyfoodapp.fragments.HomeFragment
import com.estudo.easyfoodapp.viewmodel.CategoryMealsViewModel
import retrofit2.Callback

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding : ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter:CategoryMealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMealsViewModel.obseveMealsLiveData().observe(this, Observer {mealList->
            binding.tvCategorieCount.text = mealList.size.toString()
            categoryMealsAdapter.setMealsList(mealList)

        })
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {

            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}