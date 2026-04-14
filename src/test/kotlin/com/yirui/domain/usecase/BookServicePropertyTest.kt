package com.yirui.domain.usecase

import com.yirui.domain.model.Book
import com.yirui.domain.port.BookRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.checkAll
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.string

class BookServicePropertyTest : FunSpec({
    // mock repository
    class FakeBookRepository(private val books: List<Book>) : BookRepository {
        override fun save(book: Book) {}
        override fun findAll(): List<Book> = books
    }

    //  PROPERTY 1 : sorting correct
    test("books are always sorted by title") {

        checkAll(
            Arb.list(Arb.string(minSize = 1), range = 1..20)
        ) { titles ->

            val books = titles.map { Book(it, "author") }

            val repo = FakeBookRepository(books)
            val service = BookService(repo)

            val result = service.getAllBooks()

            result.map { it.title } shouldBe titles.sorted()
        }
    }


    //  PROPERTY 2 : no data lost
    test("no book is lost after sorting") {

        checkAll(
            Arb.list(Arb.string(minSize = 1), range = 1..20)
        ) { titles ->

            val books = titles.map { Book(it, "author") }

            val repo = FakeBookRepository(books)
            val service = BookService(repo)

            val result = service.getAllBooks()

            result.size shouldBe books.size
        }
    }
})