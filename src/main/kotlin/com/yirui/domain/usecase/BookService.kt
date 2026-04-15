package com.yirui.domain.usecase;
import com.yirui.domain.model.Book;
import com.yirui.domain.port.BookRepository


class BookService(
    private val bookRepository: BookRepository
) {

    fun addBook(book: Book) {
        require(book.title.isNotBlank()) { "Le titre ne doit pas être vide" }
        require(book.author.isNotBlank()) { "L'auteur ne doit pas être vide" }

        bookRepository.save(book)
    }

    fun getAllBooks(): List<Book> {
        return bookRepository.findAll()
            .sortedBy { it.title }
    }
}