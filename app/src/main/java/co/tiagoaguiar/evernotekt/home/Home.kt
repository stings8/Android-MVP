package co.tiagoaguiar.evernotekt.home

import co.tiagoaguiar.evernotekt.model.Note

interface Home {

    interface View {
        fun displayNotes(notes: List<Note>)
        fun displayEmptyNotes()
        fun displayError(message: String)
    }

    interface Presenter {
        fun getAllNotes()
        fun stop()
    }
}