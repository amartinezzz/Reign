package com.amartinez.reign.data.repository.local

import com.amartinez.reign.domain.model.Hits
import io.reactivex.Observable

interface LocalRepository {
    fun save(hits: ArrayList<Hits>): Observable<Boolean>

    fun loadHits(): Observable<ArrayList<Hits>>
}