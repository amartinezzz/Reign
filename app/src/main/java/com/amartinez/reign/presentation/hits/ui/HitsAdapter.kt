package com.amartinez.reign.presentation.hits.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.amartinez.reign.R
import com.amartinez.reign.databinding.ItemHitsBinding
import com.amartinez.reign.domain.model.Hits
import org.ocpsoft.prettytime.PrettyTime
import java.util.*
import kotlin.collections.ArrayList

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

    fun removeItem(position: Int) {
        results.removeAt(position)
    }

    fun clear() {
        results.clear()
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
            val prettyTime = PrettyTime(Locale.getDefault())

            val title = if(hits.storyTitle != null) hits.storyTitle else hits.title
            binder?.tvTitle?.text = title?.capitalize()
            binder?.tvAuthor?.text = hits.author?.capitalize()
            binder?.tvDate?.text = prettyTime.format(hits.createdAt)
            itemView.setOnClickListener{
                val bundle = bundleOf("hits_url" to hits.storyUrl)
                navController.navigate(R.id.action_hitsFragment_to_hitsDetailFragment, bundle)
            }
        }
    }
}