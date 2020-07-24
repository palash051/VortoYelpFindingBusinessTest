package com.jbhuiyan.projects.vortoyelptest.viewmodel


import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jbhuiyan.projects.vortoyelptest.businesslogic.YelpBusinessService
import com.jbhuiyan.projects.vortoyelptest.businesslogic.YelpModel
import com.jbhuiyan.projects.vortoyelptest.di.DaggerApiComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class YelpBusinessViewModel : ViewModel() {

    @Inject
    lateinit var businessService: YelpBusinessService
    private val disposable = CompositeDisposable()
    val yelpModel = MutableLiveData<YelpModel>()
    val yelpLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    lateinit var searchText: String
    val location = MutableLiveData<Location>()

    init {
        DaggerApiComponent.create().inject(this)
    }

    companion object {
        /* Considering 5 miles radius.
        * If needed we can use a input UI component like edittext, dropdown
        * */
        const val RADIUS_IN_METERS: Int = 8050;
    }

    fun refresh() {
        fetchYelpBusinesses()
    }

    private fun fetchYelpBusinesses() {
        loading.value = true
        doAsync {
            uiThread {
                disposable.add(
                    businessService.getBusinesses(
                        searchText,
                        location.value!!.latitude,
                        location.value!!.longitude,
                        RADIUS_IN_METERS
                    ).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<YelpModel>() {
                            override fun onSuccess(value: YelpModel?) {
                                setDataOnSuccess(value)
                            }

                            override fun onError(e: Throwable?) {
                                yelpLoadError.value = true
                                loading.value = false
                            }
                        })
                )
            }
        }
    }

    private fun setDataOnSuccess(value: YelpModel?) {
        yelpModel.value = value
        yelpLoadError.value = false
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
