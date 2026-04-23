Feature: Book reservation

  Scenario: return a reserved book

    Given the following books exist
      | title                                   | author       |
      | Harry Potter and the Chamber of Secrets | J.K. Rowling |

    When the user reserves the book "Harry Potter and the Chamber of Secrets"

    And the user returns the book "Harry Potter and the Chamber of Secrets"

    Then the book "Harry Potter and the Chamber of Secrets" by "J.K. Rowling" should not be reserved