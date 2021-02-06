package com.example.demoevaluationnandita.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.demoevaluationnandita.R
import com.example.demoevaluationnandita.appdata.shared.ViewModelFactory
import com.example.demoevaluationnandita.appdata.utils.AppConfig
import com.example.demoevaluationnandita.appdata.utils.Status.*
import com.example.demoevaluationnandita.appdata.utils.Utils
import com.example.demoevaluationnandita.data.api.ApiHelper
import com.example.demoevaluationnandita.data.api.ApiServiceImpl
import com.example.demoevaluationnandita.data.api.model.Articles
import com.example.demoevaluationnandita.databinding.ActivityHomeBinding
import com.example.demoevaluationnandita.ui.adapter.HomeAdapter
import com.kaopiz.kprogresshud.KProgressHUD


class HomeActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var dataArrayList: ArrayList<Articles>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var binding: ActivityHomeBinding
    private var hud: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setupViewModel()
        initRvCategory()
        setupSwipe()
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(ApiServiceImpl()), this, application)
        ).get(HomeViewModel::class.java)

    }

    private fun initRvCategory() {

        dataArrayList = ArrayList<Articles>()
        linearLayoutManager = LinearLayoutManager(this@HomeActivity)
        binding.rvhome.layoutManager = linearLayoutManager
        homeAdapter = HomeAdapter(this@HomeActivity, dataArrayList)
        binding.rvhome.adapter = homeAdapter

    }

    private fun setupSwipe() {
        binding.swipe.setOnRefreshListener {
            if (Utils.isInternetON()) {
                hud = Utils.showLoader(this)
                homeViewModel.GetHomeData(AppConfig.COUNTRY, AppConfig.CATEGORY, AppConfig.APIKEY)
                observeData()
                binding.swipe.isRefreshing = false
            } else {
                Utils.showAlertDialog(this@HomeActivity, "No Internet!")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (Utils.isInternetON()) {
            hud = Utils.showLoader(this)
            homeViewModel.GetHomeTopHeadline(AppConfig.COUNTRY, AppConfig.APIKEY)
            homeViewModel.GetHomeData(AppConfig.COUNTRY, AppConfig.CATEGORY, AppConfig.APIKEY)

            observeData()
        } else {
            Utils.showAlertDialog(this@HomeActivity, "No Internet!")
        }
    }

    private fun observeData() {
        homeViewModel.getHomeData().observe(this, Observer {
            when (it.status) {

                SUCCESS -> {
                    if (binding.swipe.isRefreshing)
                        binding.swipe.isRefreshing = false
                    Utils.dismissLoader(hud!!)
                    it.data!!.let { resources ->
                        if (resources.articles.size > 0) {

                            dataArrayList.clear()
                            dataArrayList.addAll(resources.articles)
                            homeAdapter.notifyDataSetChanged()
                        }

                    }
                }
                LOADING -> {
                    Utils.dismissLoader(hud!!)
                }
                ERROR -> {
                    Utils.dismissLoader(hud!!)
                }
            }
        })
        homeViewModel.getHomeTopHeadline().observe(this, Observer {
            when (it.status) {

                SUCCESS -> {
                    if (binding.swipe.isRefreshing)
                        binding.swipe.isRefreshing = false
                    Utils.dismissLoader(hud!!)
                    it.data!!.let { resources ->
                        if (resources.articles.size > 0) {

                            binding.tvTitletop.text = resources.articles[0].title
                            binding.tvDesctop.text = resources.articles[0].description
                            binding.tvNametop.text = resources.articles[0].source.name
                            try {
                                Glide
                                        .with(this)
                                        .load(resources.articles[0].urlToImage)
                                        .apply(
                                                RequestOptions().placeholder(R.drawable.place_holder)
                                                        .error(R.drawable.place_holder)
                                        )
                                        .listener(object : RequestListener<Drawable?> {
                                            override fun onLoadFailed(
                                                    e: GlideException?,
                                                    model: Any,
                                                    target: Target<Drawable?>,
                                                    isFirstResource: Boolean
                                            ): Boolean {
                                                return false
                                            }

                                            override fun onResourceReady(
                                                    resource: Drawable?,
                                                    model: Any,
                                                    target: Target<Drawable?>,
                                                    dataSource: DataSource,
                                                    isFirstResource: Boolean
                                            ): Boolean {
                                                return false
                                            }
                                        })
                                        .into(binding.ivAvtartop)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }

                    }
                }
                LOADING -> {
                    Utils.dismissLoader(hud!!)
                }
                ERROR -> {
                    Utils.dismissLoader(hud!!)
                }
            }
        })

    }
}