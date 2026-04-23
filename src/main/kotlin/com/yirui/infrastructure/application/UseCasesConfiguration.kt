package com.yirui.infrastructure.application
import com.yirui.domain.model.Book
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.yirui.domain.usecase.BookService
import com.yirui.domain.port.BookRepository
import org.springframework.context.annotation.Profile


@Configuration
class UseCasesConfiguration {

    @Bean
    fun bookRepository(): BookRepository {
        return object : BookRepository {

            private val books = mutableListOf<Book>()

            override fun save(book: Book) {
                books.removeIf {
                    it.title == book.title && it.author == book.author
                }
                books.add(book)
            }

            override fun findAll(): List<Book> {
                return books
            }
            override fun findByTitleAndAuthor(title: String, author: String): Book? {
                return books.find { it.title == title && it.author == author }
            }
        }
        }

    @Bean
    fun bookService(bookRepository: BookRepository): BookService {
        return BookService(bookRepository)
    }
}






