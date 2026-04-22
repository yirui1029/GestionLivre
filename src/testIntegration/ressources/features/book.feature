Feature: manage books

  Scenario: the user creates two books and retrieves them
    Given the user creates a book "Clean Code" by "Robert Martin"
    And the user creates a book "DDD" by "Eric Evans"
    When the user gets all books
    Then the list should contain the following books
      | title       | author          |
      | Clean Code  | Robert Martin   |
      | DDD         | Eric Evans      |