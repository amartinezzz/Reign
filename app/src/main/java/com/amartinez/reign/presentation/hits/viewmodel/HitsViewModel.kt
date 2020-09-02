package com.amartinez.reign.presentation.hits.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amartinez.reign.domain.model.Hits
import com.amartinez.reign.domain.model.HitsPage
import com.amartinez.reign.domain.usecase.LoadHitsUseCase
import com.amartinez.reign.domain.usecase.local.LoadHitsLocalUseCase
import com.amartinez.reign.domain.usecase.local.SaveHitsLocalUseCase
import io.reactivex.observers.DisposableObserver

class HitsViewModel : ViewModel() {
    private var page: Int = 0

    private lateinit var loadHitsUseCase: LoadHitsUseCase
    private lateinit var saveHitsLocalUseCase: SaveHitsLocalUseCase
    private lateinit var loadHitsLocalUseCase: LoadHitsLocalUseCase
    private var hits = MutableLiveData<ArrayList<Hits>>()
    private var deletedHits = ArrayList<Hits>()

    fun setUseCase(loadHitsUseCase: LoadHitsUseCase, saveHitsLocalUseCase: SaveHitsLocalUseCase,
                   loadHitsLocalUseCase: LoadHitsLocalUseCase) {
        this.loadHitsUseCase = loadHitsUseCase
        this.saveHitsLocalUseCase = saveHitsLocalUseCase
        this.loadHitsLocalUseCase = loadHitsLocalUseCase
    }

    fun loadHits(isNetworkConnected: Boolean, ifRefreshing: Boolean): LiveData<ArrayList<Hits>> {
        if(ifRefreshing)
            hits = MutableLiveData<ArrayList<Hits>>()

        hits.also {
            if (isNetworkConnected) {
                loadHitsUseCase.execute(object : DisposableObserver<HitsPage>() {
                        override fun onNext(value: HitsPage) {
                            hits.postValue(clean(value.hits))
                            saveHits(value.hits)
                        }

                        override fun onError(e: Throwable) {
                            val hitsNotFound = ArrayList<Hits>()
                            hits.postValue(hitsNotFound)
                        }

                        override fun onComplete() {}
                    })
            } else {
                loadHitsLocalUseCase.execute(object : DisposableObserver<ArrayList<Hits>>() {
                    override fun onNext(value: ArrayList<Hits>) {
                        hits.postValue(value)
                    }

                    override fun onError(e: Throwable) {
                        val hitsNotFound = ArrayList<Hits>()
                        hits.postValue(hitsNotFound)
                    }

                    override fun onComplete() {}
                })
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

    private fun saveHits(hits: ArrayList<Hits>) {
        saveHitsLocalUseCase.setData(hits).execute(object : DisposableObserver<Boolean>() {
            override fun onComplete() {}

            override fun onNext(t: Boolean) {}

            override fun onError(e: Throwable) {}
        })
    }
}
