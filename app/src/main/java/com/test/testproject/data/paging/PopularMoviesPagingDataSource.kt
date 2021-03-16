package com.test.testproject.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.test.testproject.data.model.Movie
import com.test.testproject.data.repositories.MoviesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers

class PopularMoviesPagingDataSource(
    private val moviesRepository: MoviesRepository,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    var state = MutableLiveData<State>()
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        updateState(State.INIT_LOADING)
        val sub = moviesRepository.getPopularMovies(1)
            .subscribe({
                updateState(State.DONE)
                callback.onResult(it, null, 2)
            }, {
                it.printStackTrace()
                updateState(State.INIT_ERROR)
                setRetry { loadInitial(params, callback) }
            })
        compositeDisposable.add(sub)
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
        updateState(State.PAGE_LOADING)
        val sub = moviesRepository.getPopularMovies(params.key)
            .subscribe({
                updateState(State.DONE)
                callback.onResult(it, params.key + 1)
            }, {
                it.printStackTrace()
                updateState(State.PAGE_ERROR)
                setRetry { loadAfter(params, callback) }
            })
        compositeDisposable.add(sub)
    }

    fun retry() {
        retryCompletable?.let {
            val sub = it
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

            compositeDisposable.add(sub)
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    enum class State {
        DONE, INIT_LOADING, PAGE_LOADING, INIT_ERROR, PAGE_ERROR
    }

}