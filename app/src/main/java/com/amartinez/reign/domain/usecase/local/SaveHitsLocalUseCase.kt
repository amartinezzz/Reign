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

class SaveHitsLocalUseCase @Inject constructor(private val repository: LocalRepository) {

    private lateinit var hits: ArrayList<Hits>

    private fun createObservableUseCase(): Observable<Boolean> {
        return repository.save(hits)
    }

    fun setData(hits: ArrayList<Hits>): SaveHitsLocalUseCase {
        this.hits = hits
        return this
    }

    fun execute(disposableObserver: DisposableObserver<Boolean>) {
        Preconditions.checkNotNull<Any>(disposableObserver)
        val observable: Observable<Boolean> = createObservableUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val observer: DisposableObserver<Boolean> = observable.subscribeWith(disposableObserver)
        CompositeDisposable().add(observer)
    }
}