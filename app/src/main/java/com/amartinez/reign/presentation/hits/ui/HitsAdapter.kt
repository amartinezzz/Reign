package com.amartinez.reign.presentation.hits.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.amartinez.reign.R
import com.amartinez.reign.databinding.ItemHitsBinding
import com.amartinez.reign.domain.model.Hits

class HitsAdapter(private val context: Context,
                  private val navController: NavController) : RecyclerView.Adapter<HitsAdapter.HitsHolder>() {

    private var results: ArrayList<Hits> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HitsHolder {
        val view: View
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_hits, parent, false
        )
        view = binding.root

        return HitsHolder(view)
    }

    fun addItems(list: List<Hits>) {
        results.addAll(list)
    }

    override fun onBindViewHolder(holder: HitsHolder, position: Int) {
        holder.bind(position, results.get(position))
    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class HitsHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val touched = false

        @SuppressLint("ClickableViewAccessibility")
        fun bind(position: Int, hits: Hits) {
            val binder = DataBindingUtil.getBinding<ItemHitsBinding>(view)

            /*binder?.ivAlbum?.let {
                Glide.with(context)
                    .load(result.artworkUrl30)
                    .placeholder(R.drawable.placeholder)
                    .into(it)
            }*/
            binder?.tvArtist?.text = hits.title
            /*binder?.tvSong?.text = result.trackName
            val onClickListener = View.OnClickListener {
                val bundle = bundleOf("result" to result)
                    navController.navigate(R.id.action_searchFragment_to_detailFragment, bundle)
                }
            binder?.ivAlbum?.setOnClickListener(onClickListener)
            binder?.tvArtist?.setOnClickListener(onClickListener)
            binder?.tvSong?.setOnClickListener(onClickListener)*/
        }
    }
}