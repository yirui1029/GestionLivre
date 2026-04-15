package com.yirui.infrastructure.application
import com.yirui.domain.model.Book
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.yirui.domain.usecase.BookService
import com.yirui.domain.port.BookRepository


@Configuration
class UseCasesConfiguration {

    @Bean
    fun bookRepository(): BookRepository {
        return object : BookRepository {

            private val books = mutableListOf<Book>()

            override fun save(book: Book) {
                books.add(book)
            }

            override fun findAll(): List<Book> {
                return books
            }
        }
    }


    @Bean
    fun bookService(bookRepository: BookRepository): BookService {
        return BookService(bookRepository)
}
}