package com.assignment.powerplay.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.powerplay.client.APIClient
import com.assignment.powerplay.model.Beer
import com.assignment.powerplay.network.Resource
import com.assignment.powerplay.urlInterface.APIInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BeerViewModel : ViewModel() {
    private lateinit var sources: MutableLiveData<Resource<ArrayList<Beer>>>
    private lateinit var compositeDisposable: CompositeDisposable

    fun getBeers(page: Int): LiveData<Resource<ArrayList<Beer>>> {
        sources = MutableLiveData()
        compositeDisposable = CompositeDisposable()
        loadSources(page)
        return sources
    }

    private fun loadSources(page: Int) {
        val apiService = APIClient.getClient().create(APIInterface::class.java)
        val call = apiService.getBeers(page)

        compositeDisposable.add(
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
        )

    }

    private fun handleResponse(beers: ArrayList<Beer>) {

        if (beers != null)
            sources.postValue(Resource.success(beers))
    }

    private fun handleError(error: Throwable) {
        sources.postValue(Resource.error(error, null))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

