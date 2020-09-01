package com.amartinez.reign.data.remote

import com.amartinez.reign.data.remote.response.HitsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @Headers("Accept: application/json")
    @GET("search_by_date")
    fun search(
        @Query("query") term: String
    ): Observable<HitsResponse?>
}