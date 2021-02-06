package com.example.demoevaluationnandita.ui.adapter


import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.demoevaluationnandita.R
import com.example.demoevaluationnandita.data.api.model.Articles

import java.util.*

class HomeAdapter(
        private val context: Context,
        private val arrayList: ArrayList<Articles>
) :
        RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tv_title.text = arrayList[position].title
        holder.tv_desc.text = arrayList[position].description
        holder.tv_name.text = arrayList[position].source.name

        try {
            Glide
                    .with(context)
                    .load(arrayList[position].urlToImage)
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
                    .into(holder.iv_avtar)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        init {
        }

        override fun onClick(view: View?) {
            if (view?.id == R.id.rl_container) {

            }
        }

        val iv_avtar = itemView.findViewById(R.id.iv_avtar) as ImageView
        val tv_name = itemView.findViewById(R.id.tv_name) as TextView
        val tv_desc = itemView.findViewById(R.id.tv_desc) as TextView
        val tv_title = itemView.findViewById(R.id.tv_title) as TextView

        val rl_container = itemView.findViewById(R.id.rl_container) as RelativeLayout

    }

}