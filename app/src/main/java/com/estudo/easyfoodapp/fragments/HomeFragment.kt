package com.estudo.easyfoodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.estudo.easyfoodapp.activites.CategoryMealsActivity
import com.estudo.easyfoodapp.activites.MealActivity
import com.estudo.easyfoodapp.adapters.CategoriesAdapter
import com.estudo.easyfoodapp.adapters.MostPopularAdapter
import com.estudo.easyfoodapp.databinding.FragmentHomeBinding
import com.estudo.easyfoodapp.pojo.MealsByCategory
import com.estudo.easyfoodapp.pojo.Meal
import com.estudo.easyfoodapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter:MostPopularAdapter
    private lateinit var categoriesAdapter:CategoriesAdapter


    companion object{
        const val MEAL_ID = "com.estudo.easyfoodapp.fragments.idMeal"
        const val MEAL_NAME = "com.estudo.easyfoodapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.estudo.easyfoodapp.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.estudo.easyfoodapp.fragments.categoryName"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]

        popularItemsAdapter = MostPopularAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated (view: View,savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preparePopItemsRecyclerView()

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopItems()
        observerPopItemLivaData()
        popularItemClick()

        prepareCategoriesRecyclerView()
        homeMvvm.getCategories()
        observerCategoriesLiveData()
        onCategoryClick()


    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = {category ->

            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)

        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.apply {

            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter =categoriesAdapter
        }
    }

    private fun observerCategoriesLiveData() {
        homeMvvm.observerCategoriesLiveData().observe(viewLifecycleOwner, Observer {categories->
                categoriesAdapter.setCaategoryList(categories)


        })
    }

    private fun popularItemClick() {
        popularItemsAdapter.onItemClick = {meal ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun preparePopItemsRecyclerView() {
        binding.recViewMealPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter

        }
    }

    private fun observerPopItemLivaData() {
        homeMvvm.observerPopItemLiveData().observe(viewLifecycleOwner
        ) { mealList->


            popularItemsAdapter.setMeals(mealsList =  mealList as ArrayList<MealsByCategory> )

        }
    }

    private fun onRandomMealClick() {
       binding.randomMealCard.setOnClickListener {
           val intent = Intent(activity, MealActivity::class.java)
           intent.putExtra(MEAL_ID,randomMeal.idMeal)
           intent.putExtra(MEAL_NAME,randomMeal.strMeal)
           intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
           startActivity(intent)
       }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner,{meal ->
            Glide.with(this@HomeFragment)
                .load(meal.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        })
    }


}