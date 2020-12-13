package com.assignment.powerplay.urlInterface

import com.assignment.powerplay.model.Beer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("beers")
    fun getBeers(@Query("page") pageNo: Int): Single<ArrayList<Beer>>
}
