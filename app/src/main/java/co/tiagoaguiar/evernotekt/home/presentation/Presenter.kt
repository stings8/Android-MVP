package co.tiagoaguiar.evernotekt.home.presentation

import co.tiagoaguiar.evernotekt.home.Home
import co.tiagoaguiar.evernotekt.model.Note
import co.tiagoaguiar.evernotekt.model.RemoteDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class Presenter(
    private val view: Home.View,
    private val dataSource: RemoteDataSource
) : Home.Presenter {

    companion object {
        private const val TAG = "HomePresenter"
    }

    private val compositeDisposable = CompositeDisposable()


    private val notesObserver: DisposableObserver<List<Note>>
        get() = object : DisposableObserver<List<Note>>() {
            override fun onComplete() {
                println("complete")
            }

            override fun onNext(res: List<Note>) {
                if (res.isNotEmpty()) {
                    view.displayNotes(res)
                } else {
                    view.displayEmptyNotes()
                }
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view.displayError("Erro ao carregar notas.")
            }
        }

    private val noteObservable: Observable<List<Note>>
    get() = dataSource.listNotes()

    override fun getAllNotes() {
        val disposable = noteObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(notesObserver)

        compositeDisposable.add(disposable)
    }


    override fun stop() {
        compositeDisposable.clear()
    }
}