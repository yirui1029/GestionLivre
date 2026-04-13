package com.yirui.domain.usecase
import com.yirui.domain.model.Book
import com.yirui.domain.port.BookRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe


class BookServiceTest :FunSpec({

    class MockBookRepository : BookRepository {
        var saveCalled = false

        override fun save(book: Book) {
            saveCalled = true
        }

        override fun findAll(): List<Book> {
            return emptyList()
        }
    }

    test("should call repository when adding book") {
        val mock = MockBookRepository()
        val service = BookService(mock)

        service.addBook(Book("Test", "Auteur"))

        mock.saveCalled shouldBe true
    }
})