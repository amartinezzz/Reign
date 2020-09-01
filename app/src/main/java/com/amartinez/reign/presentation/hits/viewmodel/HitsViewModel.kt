package com.amartinez.reign.presentation.hits.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amartinez.reign.domain.model.Hits
import com.amartinez.reign.domain.model.HitsPage
import com.amartinez.reign.domain.usecase.LoadHitsUseCase
import io.reactivex.observers.DisposableObserver

class HitsViewModel() : ViewModel() {
    private var page: Int = 0

    private lateinit var loadHitsUseCase: LoadHitsUseCase
    private var hits = MutableLiveData<List<Hits>>()

    fun setUseCase(loadHitsUseCase: LoadHitsUseCase) {
        this.loadHitsUseCase = loadHitsUseCase
    }

    fun loadHits(isNetworkConnected: Boolean): LiveData<List<Hits>> {
        hits = MutableLiveData<List<Hits>>().also {
            if (isNetworkConnected) {
                loadHitsUseCase.execute(object : DisposableObserver<HitsPage>() {
                        override fun onNext(value: HitsPage) {
                            hits.postValue(value.hits)
                            //savehits(value)
                        }

                        override fun onError(e: Throwable) {
                            val hitsNotFound = ArrayList<Hits>()
                            hits.postValue(hitsNotFound)
                        }

                        override fun onComplete() {}
                    })
            } else {
                /*searchLocalUseCase.setData(term).execute(object : DisposableObserver<Search>() {
                    override fun onNext(value: Search) {
                        hits.postValue(value)
                    }

                    override fun onError(e: Throwable) {
                        val searchError = Search()
                        searchError.error = true
                        hits.postValue(searchError)
                    }

                    override fun onComplete() {}
                })*/
            }
        }

        return hits
    }

    /*fun loadMore(isNetworkConnected: Boolean) {
        page++
        hits.also {
            if (isNetworkConnected) {
                loadHitsUseCase.setData(term, limit*page)
                    .execute(object : DisposableObserver<Search>() {
                        override fun onNext(value: Search) {
                            value.term = term
                            for(i in 0 until value.results.size - limit - 1) {
                                (value.results as ArrayList).removeAt(i)
                            }

                            hits.postValue(value)
                            saveHits(value)
                        }

                        override fun onError(e: Throwable) {
                            val searchError = Search()
                            searchError.error = true
                            hits.postValue(searchError)
                        }

                        override fun onComplete() {}
                    })
            } else {
                searchLocalUseCase.setData(term).execute(object : DisposableObserver<Search>() {
                    override fun onNext(value: Search) {
                        hits.postValue(value)
                    }

                    override fun onError(e: Throwable) {
                        val searchError = Search()
                        searchError.error = true
                        hits.postValue(searchError)
                    }

                    override fun onComplete() {}
                })
            }
        }
    }

    private fun saveHits(hits: List<Hits>) {
        saveSearchUseCase.setData(search).execute(object : DisposableObserver<Boolean>() {
            override fun onComplete() {}

            override fun onNext(t: Boolean) {}

            override fun onError(e: Throwable) {}
        })
    }*/
}
