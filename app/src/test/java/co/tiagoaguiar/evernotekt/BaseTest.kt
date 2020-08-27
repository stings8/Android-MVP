package co.tiagoaguiar.evernotekt

import org.mockito.ArgumentCaptor

open class BaseTest {
    open fun <T> captureArg(argumentCaptor: ArgumentCaptor<T>): T =
        argumentCaptor.capture()
}