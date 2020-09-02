package com.amartinez.reign.presentation.hits.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amartinez.reign.R
import com.amartinez.reign.databinding.FragmentHitsBinding
import com.amartinez.reign.domain.model.Hits
import com.amartinez.reign.presentation.hits.di.DaggerHitsComponent
import com.amartinez.reign.presentation.hits.di.HitsModule
import com.amartinez.reign.presentation.hits.viewmodel.HitsViewModel
import javax.inject.Inject

class HitsFragment : Fragment() {
    @Inject
    lateinit var viewModel: HitsViewModel
    private lateinit var binder: FragmentHitsBinding
    private lateinit var adapter: HitsAdapter
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_hits, container, false)
        retainInstance = true
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        val hitsList = viewModel.loadHits(isNetworkConnected())
        hitsList.observe(viewLifecycleOwner, Observer {
            displaySearchResults(hitsList.value!!)
        })
    }

    private fun injectDependencies() {
        DaggerHitsComponent.builder().hitsModule(HitsModule(this)).build().inject(this)
    }

    private fun initAdapter() {
        adapter = HitsAdapter(requireContext(), findNavController())
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binder.rvHits.layoutManager = linearLayoutManager
        binder.rvHits.adapter = adapter
        binder.rvHits.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        binder.rvHits.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                        //viewModel.loadMore(isNetworkConnected())
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun displaySearchResults(results: List<Hits>) {
        adapter.addItems(results)
        adapter.notifyDataSetChanged()
        isLoading = false
    }

    fun error() {
        Toast.makeText(context, "No se encontro data", Toast.LENGTH_LONG).show()
    }

    private fun hideSoftKeyboard(view: View?) {
        if (view != null) {
            val imm = (view.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun isNetworkConnected() : Boolean {
        val cm =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }
}