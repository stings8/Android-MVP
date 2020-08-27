package co.tiagoaguiar.evernotekt.add.presentation

import co.tiagoaguiar.evernotekt.add.Add
import co.tiagoaguiar.evernotekt.model.Note
import co.tiagoaguiar.evernotekt.model.RemoteDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


class Presenter(
    private val view: Add.View,
    private val dataSource: RemoteDataSource
) : Add.Presenter {

    companion object {
        private const val TAG = "AddPresenter"
    }

    private val compositeDisposable = CompositeDisposable()

    private val createNoteObserver: DisposableObserver<Note>
        get() = object : DisposableObserver<Note>() {
            override fun onComplete() {
                println("complete")
            }

            override fun onNext(res: Note) {
                view.returnToHome()
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view.displayError("Erro ao criar nota")
            }
        }

    private val noteObserver: DisposableObserver<Note>
        get() = object : DisposableObserver<Note>() {
            override fun onComplete() {
                println("complete")
            }

            override fun onNext(res: Note) {
                if (res != null) {
                    view.displayNote(res.title ?: "", res.body ?: "")
                } else {
                    // no data
                }
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view.displayError("Erro ao trazer nota")
            }
        }

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun createNote(title: String, body: String) {
        if (title.isEmpty() || body.isEmpty()) {
            view.displayError("Titulo e corpo da nota devem ser preenchidos")
            return
        }
        val note = Note(title = title, body = body)
        val disposable = dataSource.createNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(createNoteObserver)

        compositeDisposable.add(disposable)

    }


    override fun getNote(id: Int) {
        val disposable = dataSource.getNote(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(noteObserver)

        compositeDisposable.add(disposable)
    }
}