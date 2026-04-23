package com.yirui.domain.steps

import com.yirui.DomainApplication
import io.cucumber.datatable.DataTable
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@CucumberContextConfiguration
@SpringBootTest(
    classes = [DomainApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class BookStepDefs {

    @LocalServerPort
    private var port: Int = 0

    @Before
    fun setup() {
        RestAssured.baseURI = "http://localhost:$port"
    }

    // ------------------------
    // GIVEN
    // ------------------------
    @Given("the following books exist")
    fun the_following_books_exist(dataTable: DataTable) {

        dataTable.asMaps().forEach { row ->

            val body = mapOf(
                "title" to row["title"],
                "author" to row["author"]
            )

            RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .`when`()
                .post("/books")
                .then()
                .log().all()
                .statusCode(201)
        }
    }

    // ------------------------
    // RESERVE
    // ------------------------
    @When("the user reserves the book {string}")
    fun reserveBook(title: String) {

        val body = mapOf(
            "title" to title,
            "author" to "J.K. Rowling"
        )

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(body)
            .log().all()

            .`when`()
            .patch("/books/reserve")

            .then()
            .log().all()
            .statusCode(204)
    }

    // ------------------------
    // RETURN
    // ------------------------
    @When("the user returns the book {string}")
    fun returnBook(title: String) {

        RestAssured.given()
            .queryParam("title", title)
            .queryParam("author", "J.K. Rowling")
            .log().all()
            .`when`()
            .patch("/books/return")
            .then()
            .log().all()
            .statusCode(204)
    }

    // ------------------------
    // ASSERT RESERVED
    // ------------------------
    @Then("the book {string} by {string} should be reserved")
    fun shouldBeReserved(title: String, author: String) {

        val books = RestAssured.given()
            .`when`()
            .get("/books")
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getList<Map<String, Any>>("")

        val book = books.find {
            it["title"] == title && it["author"] == author
        }

        book?.get("reserved") shouldBe true
    }

    // ------------------------
    // ASSERT NOT RESERVED
    // ------------------------
    @Then("the book {string} by {string} should not be reserved")
    fun shouldNotBeReserved(title: String, author: String) {

        val books = RestAssured.given()
            .`when`()
            .get("/books")
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getList<Map<String, Any>>("")

        val book = books.find {
            it["title"] == title && it["author"] == author
        }

        book?.get("reserved") shouldBe false
    }
}