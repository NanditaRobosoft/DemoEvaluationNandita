package com.example.demoevaluationnandita.data.api


class ApiHelper(private val apiService: ApiService) {
    fun fetchTopHeadline(country:String,apikey:String)= apiService.fetchTopHeadline(country,apikey)
    fun fetchData(country:String,category:String,apikey:String) = apiService.fetchData(country,category,apikey)
}