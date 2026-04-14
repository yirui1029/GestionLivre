package com.yirui.domain.usecase
import com.yirui.domain.model.Book
import com.yirui.domain.port.BookRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow


class BookServiceTest :FunSpec({

    class MockBookRepository : BookRepository {
        var saveCalled = false
        val books= mutableListOf<Book>()

        override fun save(book: Book) {
            saveCalled = true
            books.add(book)
        }

        override fun findAll(): List<Book> {
            return books
        }
    }
    // test sur function add book avec boolean pour tester
    test("should call repository when adding book") {
        val mock = MockBookRepository()
        val service = BookService(mock)

        service.addBook(Book("Test", "Auteur"))

        mock.saveCalled shouldBe true
    }
    // function pour voir si les livrés sont sorted
    test("should return books sorted by title") {
        val mock = MockBookRepository()
        val service = BookService(mock)

        service.addBook(Book("Z Book", "A"))
        service.addBook(Book("A Book", "B"))
        service.addBook(Book("M Book", "C"))

        val result = service.getAllBooks()
        println(mock.books)

        result.map { it.title } shouldBe listOf("A Book", "M Book", "Z Book")
    }
    // test pour voir si le title de book est absent, si le livre va ajouter ou pas
    test("should throw if title is empty") {
        val mock= MockBookRepository()
        val service = BookService(mock)

        shouldThrow<IllegalArgumentException> {
            service.addBook(Book("", "Author"))
        }
    }
    // test pour voir si l'auteur est absent, si le livre va ajouter ou pas avec function add
    test("should throw if author is empty") {
        val mock= MockBookRepository()
        val service = BookService(mock)

        shouldThrow<IllegalArgumentException> {
            service.addBook(Book("title", ""))
        }
    }
})