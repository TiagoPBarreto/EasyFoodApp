package com.estudo.easyfoodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estudo.easyfoodapp.pojo.*

import com.estudo.easyfoodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {
    private  var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemLivaData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeal(){

        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){

                    val randomMeal: Meal = response.body()!!.meals[0]

                    randomMealLiveData.value = randomMeal

                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


    fun getPopItems(){

        RetrofitInstance.api.getPopItems("Seafood").enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {

                if (response.body() != null){

                    popularItemLivaData.value = response.body()!!.meals

                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
               Log.d("HomeFragment",t.message.toString())
            }


        })
    }
    fun getCategories(){

        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{

            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                response.body()?.let { category ->

                    categoriesLiveData.postValue(category.categories)
                }

            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {

                Log.d("HomeViewModel",t.message.toString())
            }
        })

    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observerPopItemLiveData():LiveData<List<MealsByCategory>>{

        return popularItemLivaData
    }

    fun observerCategoriesLiveData(): LiveData<List<Category>>{

        return categoriesLiveData
    }
}


