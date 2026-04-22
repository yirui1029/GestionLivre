package com.yirui.infrastructure.application.driven.postgres
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
            INSERT INTO book(title, author, reserved)
            VALUES (:title, :author, :reserved)
            """.trimIndent(),
            mapOf(
                "title" to book.title,
                "author" to book.author,
                "reserved" to book.reserved
            )
        )
    }

    fun findAll(): List<Book> {
        return jdbc.query(
            "SELECT title, author, reserved FROM book"
        ) { rs, _ ->
            Book(
                title = rs.getString("title"),
                author = rs.getString("author"),
                reserved = rs.getBoolean("reserved")
            )
        }
    }

    fun reserveBook(title: String, author: String): Boolean {
        return jdbc.update(
            """
            UPDATE book
            SET reserved = true
            WHERE title = :title
            AND author = :author
            AND reserved = false
            """.trimIndent(),
            mapOf(
                "title" to title,
                "author" to author
            )
        ) == 1
    }
}


