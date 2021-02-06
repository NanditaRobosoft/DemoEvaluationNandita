package com.example.demoevaluationnandita.ui.home

import android.app.Application
import android.content.Context
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demoevaluationnandita.appdata.shared.BaseViewModel
import com.example.demoevaluationnandita.appdata.utils.Resource
import com.example.demoevaluationnandita.data.api.model.NewsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
        var context: Context,
        application: Application,
        homeRepository: HomeRepository
) : BaseViewModel<Nullable>(context, application) {
    private var homeRepository: HomeRepository? = null
    private val homeData = MutableLiveData<Resource<NewsResponse>>()
    private val homeTopHeadline = MutableLiveData<Resource<NewsResponse>>()

    init {
        this.homeRepository = homeRepository
    }


    fun GetHomeData(country: String, category: String, apikey: String) {

        homeData.postValue(Resource.loading(null))
        getCompositeDisposable()!!.add(
                homeRepository!!.doGetData(country, category, apikey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ bannerResponse ->

                            homeData.postValue(Resource.success(bannerResponse))
                        }, { throwable ->

                            homeData.postValue(Resource.error("Something Went Wrong", null))
                        })
        )

    }

    fun GetHomeTopHeadline(country: String, apikey: String) {

        homeData.postValue(Resource.loading(null))
        getCompositeDisposable()!!.add(
                homeRepository!!.doGetTopHeadline(country, apikey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ bannerResponse ->

                            homeTopHeadline.postValue(Resource.success(bannerResponse))
                        }, { throwable ->

                            homeTopHeadline.postValue(Resource.error("Something Went Wrong", null))
                        })
        )

    }


    fun getHomeData(): LiveData<Resource<NewsResponse>> {
        return homeData
    }

    fun getHomeTopHeadline(): LiveData<Resource<NewsResponse>> {
        return homeTopHeadline
    }
}