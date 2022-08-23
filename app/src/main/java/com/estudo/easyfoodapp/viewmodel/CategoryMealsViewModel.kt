package com.estudo.easyfoodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estudo.easyfoodapp.pojo.MealsByCategory
import com.estudo.easyfoodapp.pojo.MealsByCategoryList
import com.estudo.easyfoodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel(){

    val mealsLiveDate = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName:String){

        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList>{

            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body().let {mealsList ->

                    mealsLiveDate.postValue(mealsList!!.meals)


                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("category",t.message.toString())
            }
        })
    }
    fun obseveMealsLiveData():LiveData<List<MealsByCategory>>{

        return mealsLiveDate
    }
}