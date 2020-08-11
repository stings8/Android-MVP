package co.tiagoaguiar.evernotekt.model

class User (private val note: Note) {

    fun showNoteTitle() {
        println(" titulo: ${note.title}")
    }

}