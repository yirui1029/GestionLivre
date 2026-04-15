package com.yirui.infrastructure.driving.controller
import com.yirui.domain.model.Book
import com.yirui.domain.usecase.BookService
import org.springframework.web.bind.annotation.*
import com.yirui.infrastructure.driving.controller.dto.BookDTO
import org.springframework.http.HttpStatus


@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService
) {

    @GetMapping
    fun getBooks(): List<BookDTO> {
        return bookService.getAllBooks().map {
            BookDTO(it.title, it.author)
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@RequestBody dto: BookDTO) {
        bookService.addBook(
            Book(dto.title, dto.author)
        )
    }
}