package com.yirui.domain.usecase
import com.yirui.domain.model.Book

interface BookService {
    // ajouter les nouveaux livres
    fun addBook(title: String, author: String)

    // lister tous les livres
    fun listBooks(): List<Book>
    // trouver un livre par le title de ce livre
    fun findBookByTitle(title: String):Book?
    // trouver les livres d'un author
    fun findBooksByAuthor(author: String ):List<Book>

}