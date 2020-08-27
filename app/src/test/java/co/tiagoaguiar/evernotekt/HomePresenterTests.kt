package co.tiagoaguiar.evernotekt

import co.tiagoaguiar.evernotekt.home.Home
import co.tiagoaguiar.evernotekt.home.presentation.Presenter
import co.tiagoaguiar.evernotekt.model.Note
import co.tiagoaguiar.evernotekt.model.RemoteDataSource
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomePresenterTests {

    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulerRule()

    @Mock
    private lateinit var mockView: Home.View

    @Mock
    private lateinit var mockDataSource: RemoteDataSource

    lateinit var homePresenter: Home.Presenter

    private val fakeNotes: List<Note>
        get() = arrayListOf(
            Note(1, "Nota teste", "Descrição da nota teste", "25/08/2020", "Teste nota"),
            Note(2, "Nota teste 2", "Descrição da nota teste 2", "26/08/2020", "Teste nota 2")
        )

    @Before
    fun setup() {
        homePresenter = Presenter(mockView, mockDataSource)
    }

   @Test
   fun `test must get all notes`() {
        // Given
        Mockito.doReturn(Observable.just(fakeNotes)).`when`(mockDataSource).listNotes()

        // When
        homePresenter.getAllNotes()

        //then
        Mockito.verify(mockDataSource).listNotes()
        Mockito.verify(mockView).displayNotes(fakeNotes)
    }

    @Test
    fun `test must show empty notes`() {
        // Given
        Mockito.doReturn(Observable.just(arrayListOf<Note>())).`when`(mockDataSource).listNotes()

        // When
        homePresenter.getAllNotes()

        //then
        Mockito.verify(mockDataSource).listNotes()
        Mockito.verify(mockView).displayEmptyNotes()
    }
}