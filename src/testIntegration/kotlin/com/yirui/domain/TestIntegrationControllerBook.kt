package com.yirui.domain
import com.fasterxml.jackson.databind.ObjectMapper
import com.yirui.infrastructure.driving.controller.dto.BookDTO
import com.yirui.domain.usecase.BookService
import com.yirui.domain.model.Book
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import io.mockk.every
import io.mockk.verify
import com.ninjasquad.springmockk.MockkBean
import com.yirui.infrastructure.driving.controller.BookController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [BookController::class])
class TestIntegrationControllerBook {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockkBean
    lateinit var bookService: BookService

    @Test
    fun `GET books should return list`() {

        every { bookService.getAllBooks() } returns listOf(
            Book("Clean Code", "Robert Martin")
        )

        mockMvc.perform(get("/books"))
            .andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$[0].title").value("Clean Code"))
            .andExpect(jsonPath("$[0].author").value("Robert Martin"))

        verify { bookService.getAllBooks() }
    }

    @Test
    fun `POST book should create book`() {

        val dto = BookDTO("Clean Code", "Robert Martin")

        every { bookService.addBook(any()) } returns Unit

        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
            .andDo(print())
            .andExpect(status().isCreated)

        verify { bookService.addBook(any()) }
    }

    @Test
    fun `POST book should return 400 when invalid`() {

        val dto = BookDTO("", "")

        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )

            .andDo(print())
            .andExpect(status().isBadRequest)
    }
}