package com.haqueit.question.app.retrofit
import com.example.konamoniruzzaman.model.CategoryResponse
import io.reactivex.Single
import retrofit2.http.*


interface Api {

    @GET("get_categories")
    fun category(
    ): Single<List<CategoryResponse>>
}