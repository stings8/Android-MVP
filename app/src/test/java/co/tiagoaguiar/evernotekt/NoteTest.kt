package co.tiagoaguiar.evernotekt

import co.tiagoaguiar.evernotekt.model.Note
import org.junit.Assert
import org.junit.Test

class NoteTest {

    @Test
    fun `teste should format date pattern to month and year`() {
        val note = Note(title = "notaA", body = "Conteudo NotaA", date = "9/08/2020")

        Assert.assertEquals("Ago 2020", note.createDate)
    }

    @Test
    fun `teste should format date case empty`() {
        val note = Note(title = "notaA", body = "Conteudo NotaA", date = "")

        Assert.assertEquals("", note.createDate)
    }

    @Test
    fun `teste should format date case null`() {
        val note = Note(title = "notaA", body = "Conteudo NotaA")

        Assert.assertEquals("", note.createDate)
    }
}