package com.matheus.bianchini.nybooksapp.presentation.books

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.matheus.bianchini.nybooksapp.R
import com.matheus.bianchini.nybooksapp.data.BooksResult
import com.matheus.bianchini.nybooksapp.data.model.Book
import com.matheus.bianchini.nybooksapp.data.repository.BooksRepository
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify


@RunWith(MockitoJUnitRunner::class)
//utilizado para iniciar o Mock
class BooksViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var booksLiveData: Observer<List<Book>>

    @Mock
    private lateinit var viewFlipperLiveData: Observer<Pair<Int, Int?>>

    private lateinit var viewModel: BooksViewModel

    @Test
    fun `when viewModel getBooks return success then set booksLiveData`() {
        //Arrange = organização para executar o teste
        val booksList = listOf(
            Book(
                "Title",
                "Author",
                "description"
            )
        )
        val resultSuccess = MockBooksRepository(BooksResult.Success(booksList))
        viewModel = BooksViewModel(resultSuccess)

        viewModel.booksLiveData.observeForever(booksLiveData)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveData)

        //Action = ação
        viewModel.getBooks()

        //Assert = validação
        verify(booksLiveData).onChanged(booksList)
        verify(viewFlipperLiveData).onChanged(Pair(1, null))
    }

    @Test
    fun `when viewModel getBooks return server error then set booksLiveData`() {
        //Arrange = organização para executar o teste
        val serverError = MockBooksRepository(BooksResult.ServerError)
        viewModel = BooksViewModel(serverError)

        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveData)

        //Action = ação
        viewModel.getBooks()

        //Assert = validação
        verify(viewFlipperLiveData).onChanged(Pair(2, R.string.error_generic))
    }

    @Test
    fun `when viewModel getBooks return api error 401 then set booksLiveData`() {
        //Arrange = organização para executar o teste
        val apiError = MockBooksRepository(BooksResult.ApiError(401))
        viewModel = BooksViewModel(apiError)

        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveData)

        //Action = ação
        viewModel.getBooks()

        //Assert = validação
        verify(viewFlipperLiveData).onChanged(Pair(2, R.string.error_unauthorized))
    }

    @Test
    fun `when viewModel getBooks return api error then set booksLiveData`() {
        //Arrange = organização para executar o teste
        val apiError = MockBooksRepository(BooksResult.ApiError(412))
        viewModel = BooksViewModel(apiError)

        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveData)

        //Action = ação
        viewModel.getBooks()

        //Assert = validação
        verify(viewFlipperLiveData).onChanged(Pair(2, R.string.error_generic))
    }
}

class MockBooksRepository(private val result: BooksResult) : BooksRepository {
    override fun getBooks(booksResultCallback: (result: BooksResult) -> Unit) {
        booksResultCallback(result)
    }
}