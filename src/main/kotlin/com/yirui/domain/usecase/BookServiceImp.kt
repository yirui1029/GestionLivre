package com.yirui.domain.usecase
import com.yirui.domain.model.Book
import com.yirui.domain.usecase.BookService

class BookServiceImp : BookService{
    private val books = mutableListOf<Book>()

    // Ajouter un livre
    override fun addBook(title: String, author: String) {
        books.add(Book(title, author))
    }

    // 📚 Lister tous les livres (tri alphabétique)
    override fun listBooks(): List<Book> {
        return books.sortedBy { it.title }
    }

    // 🔎 Trouver un livre par titre
    override fun findBookByTitle(title: String): Book? {
        return books.find { it.title.equals(title, ignoreCase = true) }
    }

    // 🔎 Trouver les livres par auteur
    override fun findBooksByAuthor(author: String): List<Book> {
        return books.filter { it.author.equals(author, ignoreCase = true) }
    }

}