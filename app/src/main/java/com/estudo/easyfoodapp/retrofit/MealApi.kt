package com.estudo.easyfoodapp.retrofit

import com.estudo.easyfoodapp.pojo.CategoryList
import com.estudo.easyfoodapp.pojo.MealsByCategoryList
import com.estudo.easyfoodapp.pojo.MealList
import com.estudo.easyfoodapp.pojo.MealsByCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(
        @Query("i") id:String
    ) :Call<MealList>

    @GET("filter.php")
    fun getPopItems(@Query("c")categoryName:String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c")categoryName: String) : Call<MealsByCategoryList>
}