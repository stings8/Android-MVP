package co.tiagoaguiar.evernotekt.model

import co.tiagoaguiar.evernotekt.network.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class RemoteDataSource {

    fun listNotes() : Observable<List<Note>> {
        return RetrofitClient.evernoteApi
            .listNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getNote(noteId: Int) : Observable<Note> {
        return RetrofitClient.evernoteApi
            .getNote(noteId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createNote(note: Note) : Observable<Note> {
        return RetrofitClient.evernoteApi
            .createNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createNoteFromUser(user: User) {
        user.showNoteTitle()
    }

}