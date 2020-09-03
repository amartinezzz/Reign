package com.amartinez.reign.domain.usecase

import com.amartinez.reign.data.repository.Repository
import com.amartinez.reign.domain.model.HitsPage
import dagger.internal.Preconditions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoadHitsUseCase @Inject constructor(private val repository: Repository) {

    private val term: String = "android"
    private var page: Int = 0

    private fun createObservableUseCase(): Observable<HitsPage> {
        return repository.loadHits(term, page)
    }

    fun setData(page: Int): LoadHitsUseCase {
        this.page = page
        return this
    }

    fun execute(disposableObserver: DisposableObserver<HitsPage>) {
        Preconditions.checkNotNull<Any>(disposableObserver)
        val observable: Observable<HitsPage> = createObservableUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val observer: DisposableObserver<HitsPage> = observable.subscribeWith(disposableObserver)
        CompositeDisposable().add(observer)
    }
}