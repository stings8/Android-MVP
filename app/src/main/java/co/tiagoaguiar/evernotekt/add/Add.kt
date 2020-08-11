package co.tiagoaguiar.evernotekt.add

import co.tiagoaguiar.evernotekt.model.Note

interface Add {

    interface View {
        fun displayError(message: String)
        fun returnToHome()
        fun displayNote(title: String, body: String)

    }

    interface Presenter {
        fun createNote(title: String, body: String)
        fun getNote(noteId: Int)
        fun stop()
    }
}