package com.yirui.domain.port
import com.yirui.domain.model.Book

interface BookRepository {
    fun save(book:Book)
    fun findAll(): List<Book>
    fun findByTitleAndAuthor(title: String, author: String): Book?

}