package com.amartinez.reign.data.repository

import com.amartinez.reign.domain.model.HitsPage
import io.reactivex.Observable

interface Repository {
    fun loadHits(
        term: String
    ): Observable<HitsPage>
}