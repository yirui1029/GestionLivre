package com.yirui.domain.usecase

import com.yirui.domain.model.Book
import com.yirui.domain.port.BookRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow

class BookServiceReserveTest : FunSpec({

    class MockBookRepository : BookRepository {
        val books = mutableListOf<Book>()

        override fun save(book: Book) {
            books.removeIf { it.title == book.title }
            books.add(book)
        }

        override fun findAll(): List<Book> = books
        override fun findByTitleAndAuthor(title: String, author: String): Book? {
            return books.find { it.title == title && it.author == author }
        }
    }

    test("should reserve a book successfully") {
        val repo = MockBookRepository()
        val service = BookService(repo)

        repo.save(Book("Clean Code", "Robert Martin"))

        val result = service.reserveBook("Clean Code", "Robert Martin")

        result.reserved shouldBe true
    }

    test("should not reserve an already reserved book") {
        val repo = MockBookRepository()
        val service = BookService(repo)

        repo.save(Book("Clean Code", "Robert Martin", true))

        shouldThrow<IllegalArgumentException> {
            service.reserveBook("Clean Code","Robert Martin")
        }
    }

    test("should throw if book does not exist") {
        val repo = MockBookRepository()
        val service = BookService(repo)

        shouldThrow<IllegalArgumentException> {
            service.reserveBook("Unknown","Unknown")
        }
    }

    test("should update repository with reserved book") {
        val repo = MockBookRepository()
        val service = BookService(repo)

        repo.save(Book("Clean Code", "Robert Martin"))

        service.reserveBook("Clean Code","Robert Martin")

        repo.findAll().first().reserved shouldBe true
    }
})