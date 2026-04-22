package com.yirui.infrastructure.driving.controller
import com.yirui.domain.model.Book
import com.yirui.domain.usecase.BookService
import org.springframework.web.bind.annotation.*
import com.yirui.infrastructure.driving.controller.dto.BookDTO
import org.springframework.http.HttpStatus
import jakarta.validation.Valid



@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService
) {

    @GetMapping
    fun getBooks(): List<BookDTO> {
        return bookService.getAllBooks().map {
            BookDTO(
                title =it.title,
                author=it.author,
                reserved=it.reserved
            )
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@RequestBody @Valid dto: BookDTO) {
        bookService.addBook(
            Book(dto.title, dto.author)
        )
    }
    // ajouter mapping reserve pour patch quand un livre a ete reservé
    @PatchMapping("/reserve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun reserveBook(@RequestBody dto: BookDTO) {
        bookService.reserveBook(dto.title, dto.author)
    }
}