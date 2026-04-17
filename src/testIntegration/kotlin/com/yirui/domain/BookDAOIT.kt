package com.yirui

import com.yirui.domain.model.Book
import com.yirui.infrastructure.driven.postgres.BookDAO
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("testIntegration")
class BookDAOIT : FunSpec() {

    @Autowired
    lateinit var bookDAO: BookDAO

    @Autowired
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    override fun extensions() = listOf(SpringExtension)

    init {
        beforeTest {
            jdbcTemplate.update("DELETE FROM book", mapOf<String, Any>())
        }

        test("should save a book") {
            bookDAO.save(Book("Clean Code", "Robert Martin"))
            val result = bookDAO.findAll()
            assert(result.size == 1)
        }

        test("should return empty list") {
            val result = bookDAO.findAll()
            assert(result.isEmpty())
        }

        test("should save multiple books") {
            bookDAO.save(Book("Clean Code", "Robert Martin"))
            bookDAO.save(Book("DDD", "Eric Evans"))
            val result = bookDAO.findAll()
            assert(result.size == 2)
        }
    }
}