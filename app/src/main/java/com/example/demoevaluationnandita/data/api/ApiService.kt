package com.example.demoevaluationnandita.data.api


import com.example.demoevaluationnandita.data.api.model.NewsResponse
import io.reactivex.Observable

interface ApiService {
    fun fetchTopHeadline(country:String,apikey:String): Observable<NewsResponse>
    fun fetchData(country:String,category:String,apikey:String): Observable<NewsResponse>
}