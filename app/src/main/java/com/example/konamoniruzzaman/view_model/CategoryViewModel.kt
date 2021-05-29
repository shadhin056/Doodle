package com.example.konamoniruzzaman.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konamoniruzzaman.model.CategoryResponse
import com.haqueit.question.app.retrofit.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CategoryViewModel : ViewModel(){

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()

    var categoryListResponse = MutableLiveData<List<CategoryResponse>>();
    var response_error = MutableLiveData<Boolean>();

    fun doCategory(){

        disposable.add(apiService.category()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<CategoryResponse>>() {
                override fun onSuccess(model: List<CategoryResponse>) {
                    model?.let {
                        categoryListResponse.value = model
                    }

                }
                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    response_error.value=true
                }

            })
        )
    }
}
