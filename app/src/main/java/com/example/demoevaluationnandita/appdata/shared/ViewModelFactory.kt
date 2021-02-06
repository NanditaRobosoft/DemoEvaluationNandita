package com.example.demoevaluationnandita.appdata.shared

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demoevaluationnandita.data.api.ApiHelper
import com.example.demoevaluationnandita.ui.home.HomeRepository
import com.example.demoevaluationnandita.ui.home.HomeViewModel


class ViewModelFactory(
        private val apiHelper: ApiHelper, private val context: Context,
        private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(context, application, HomeRepository(apiHelper)) as T
        } else
            throw IllegalArgumentException("Unknown class name")
    }

}