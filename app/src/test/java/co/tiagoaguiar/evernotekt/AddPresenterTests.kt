package co.tiagoaguiar.evernotekt

import co.tiagoaguiar.evernotekt.add.Add
import co.tiagoaguiar.evernotekt.add.presentation.Presenter
import co.tiagoaguiar.evernotekt.model.Note
import co.tiagoaguiar.evernotekt.model.RemoteDataSource
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddPresenterTests : BaseTest(){

    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulerRule()

    @Mock
    private lateinit var mockView: Add.View

    @Mock
    private lateinit var mockDataSource: RemoteDataSource

    @Captor
    private lateinit var noteArgCaptor: ArgumentCaptor<Note>

    lateinit var addPresenter: Add.Presenter


    @Before
    fun setup() {
        addPresenter = Presenter(mockView, mockDataSource)
    }

   @Test
   fun `test must not add note with empty body`() {

        // When
        addPresenter.createNote("", "")

        //then
        Mockito.verify(mockView).displayError("Titulo e corpo da nota devem ser preenchidos")
    }

    @Test
    fun `test must not add`() {
        // Given
        val note = Note(title = "Nota teste", body = "corpo nota teste")
        Mockito.doReturn(Observable.just(note)).`when`(mockDataSource).createNote(captureArg(
            noteArgCaptor
        ))

        // When
        addPresenter.createNote("Nota teste", "corpo nota teste")

        //then
        Mockito.verify(mockView).displayNote(captureArg(noteArgCaptor))
    }

}