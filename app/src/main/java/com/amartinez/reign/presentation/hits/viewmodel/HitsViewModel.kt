package com.amartinez.reign.presentation.hits.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amartinez.reign.domain.model.Hits
import com.amartinez.reign.domain.model.HitsPage
import com.amartinez.reign.domain.usecase.LoadHitsUseCase
import io.reactivex.observers.DisposableObserver

class HitsViewModel : ViewModel() {
    private var page: Int = 0

    private lateinit var loadHitsUseCase: LoadHitsUseCase
    private var hits = MutableLiveData<ArrayList<Hits>>()
    private var deletedHits = ArrayList<Hits>()

    fun setUseCase(loadHitsUseCase: LoadHitsUseCase) {
        this.loadHitsUseCase = loadHitsUseCase
    }

    fun loadHits(isNetworkConnected: Boolean, ifRefreshing: Boolean): LiveData<ArrayList<Hits>> {
        if(ifRefreshing)
            hits = MutableLiveData<ArrayList<Hits>>()

        hits.also {
            if (isNetworkConnected) {
                loadHitsUseCase.execute(object : DisposableObserver<HitsPage>() {
                        override fun onNext(value: HitsPage) {
                            hits.postValue(clean(value.hits))
                            //savehits(value)
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
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

    fun loadMore(isNetworkConnected: Boolean) {
        page++
        hits.also {
            if (isNetworkConnected) {
                loadHitsUseCase.setData(page)
                    .execute(object : DisposableObserver<HitsPage>() {
                        override fun onNext(value: HitsPage) {
                            hits.postValue(value.hits)
                            //saveHits(value)
                        }

                        override fun onError(e: Throwable) {}

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
    }

    private fun ArrayList<Hits>.removeHit(hits: Hits) {
        this.forEach {
            if(it.storyId == hits.storyId) {
                remove(it)
                return
            }
        }
    }

    private fun clean(hits: ArrayList<Hits>): ArrayList<Hits> {
        deletedHits.forEach {
            hits.removeHit(it)
        }

        return hits
    }

    fun deleteHits(position: Int) {
        hits.value?.get(position)?.let { deletedHits.add(it) }
    }

    /*private fun saveHits(hits: List<Hits>) {
        saveSearchUseCase.setData(search).execute(object : DisposableObserver<Boolean>() {
            override fun onComplete() {}

            override fun onNext(t: Boolean) {}

            override fun onError(e: Throwable) {}
        })
    }*/
}
