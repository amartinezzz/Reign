package com.amartinez.reign.presentation.hitsdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.amartinez.reign.R
import com.amartinez.reign.databinding.FragmentHitsDetailBinding


class HitsDetailFragment : Fragment() {
    private lateinit var binder: FragmentHitsDetailBinding
    private var isLoading: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_hits_detail, container, false)
        retainInstance = true
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = requireArguments().getString("hits_url")
        binder.webView.loadUrl(url)

        val onClickListener = View.OnClickListener {
            activity?.onBackPressed()
        }
        binder.ivBack.setOnClickListener(onClickListener)
        binder.tvBack.setOnClickListener(onClickListener)
    }
}