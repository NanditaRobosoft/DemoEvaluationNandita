package com.example.demoevaluationnandita.data.api


import com.example.demoevaluationnandita.data.api.model.NewsResponse
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable

class ApiServiceImpl : ApiService {
    override fun fetchTopHeadline(country: String, apikey: String): Observable<NewsResponse> {
        return Rx2AndroidNetworking.get(ApiEndPoints.NEWS)
                .addQueryParameter("country",country)
                .addQueryParameter("apiKey",apikey)
                .build()
                .getObjectObservable(NewsResponse::class.java)
    }

    override fun fetchData(country:String,category:String,apikey:String): Observable<NewsResponse> {
        return Rx2AndroidNetworking.get(ApiEndPoints.NEWS)
            .addQueryParameter("country",country)
            .addQueryParameter("category",category)
            .addQueryParameter("apiKey",apikey)
            .build()
            .getObjectObservable(NewsResponse::class.java)
    }

}