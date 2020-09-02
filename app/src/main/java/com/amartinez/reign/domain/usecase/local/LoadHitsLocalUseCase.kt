package com.amartinez.reign.domain.usecase.local

import com.amartinez.reign.data.repository.local.LocalRepository
import com.amartinez.reign.domain.model.Hits
import dagger.internal.Preconditions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoadHitsLocalUseCase @Inject constructor(private val repository: LocalRepository) {

    private fun createObservableUseCase(): Observable<ArrayList<Hits>> {
        return repository.loadHits()
    }

    fun execute(disposableObserver: DisposableObserver<ArrayList<Hits>>) {
        Preconditions.checkNotNull<Any>(disposableObserver)
        val observable: Observable<ArrayList<Hits>> = createObservableUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val observer: DisposableObserver<ArrayList<Hits>> = observable.subscribeWith(disposableObserver)
        CompositeDisposable().add(observer)
    }
}