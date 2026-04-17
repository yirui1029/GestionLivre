package com.yirui.infrastructure.driven.postgres

import com.yirui.domain.model.Book
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class BookDAO(
    private val jdbc: NamedParameterJdbcTemplate
) {

    fun save(book: Book) {
        jdbc.update(
            """
            INSERT INTO book(title, author)
            VALUES (:title, :author)
            """.trimIndent(),
            mapOf(
                "title" to book.title,
                "author" to book.author
            )
        )
    }

    fun findAll(): List<Book> {
        return jdbc.query(
            "SELECT title, author FROM book"
        ) { rs, _ ->
            Book(
                title = rs.getString("title"),
                author = rs.getString("author")
            )
        }
    }
}