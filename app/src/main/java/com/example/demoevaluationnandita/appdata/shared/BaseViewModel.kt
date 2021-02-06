package com.example.demoevaluationnandita.appdata.shared

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

open class BaseViewModel<N>(
    context: Context,
    application: Application
) : AndroidViewModel(application) {

    private var mNavigator: WeakReference<N>? = null

    private var mCompositeDisposable: CompositeDisposable? = null
    private var context: Context? = null
    private var application1: Application? = null

    init {
        mCompositeDisposable = CompositeDisposable()
        this.context = context
    }

    fun getNavigator(): N? {
        return mNavigator!!.get()
    }

    override fun onCleared() {
        mCompositeDisposable!!.dispose()
        super.onCleared()
    }

    fun getCompositeDisposable(): CompositeDisposable? {
        return mCompositeDisposable
    }

}