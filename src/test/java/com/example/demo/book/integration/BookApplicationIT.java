package com.example.demo.book.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.book.Book;
import com.example.demo.book.SaveBookRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookApplicationIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String booksURL;

    @BeforeEach
    public void setUp() {
        booksURL = "http://localhost:" + port + "/books";
    }

    @Test
    public void itCanSaveAndRetrieveABook() {
        Book createdBook = this.restTemplate.postForObject(
            booksURL,
            new SaveBookRequest("author", "title"),
            Book.class
        );

        assertThat(createdBook.getAuthor()).isEqualTo("author");
        assertThat(createdBook.getTitle()).isEqualTo("title");

        Book retrievedBook = this.restTemplate.getForObject(booksURL + "/" + createdBook.getId(), Book.class);

        assertThat(retrievedBook).isEqualTo(createdBook);
    }

    @Test
    public void itCanPublishABook() {
        Book createdBook = this.restTemplate.postForObject(
            booksURL,
            new SaveBookRequest("author", "title"),
            Book.class
        );

        assertThat(createdBook.getAuthor()).isEqualTo("author");
        assertThat(createdBook.getTitle()).isEqualTo("title");

        this.restTemplate.postForObject(booksURL + "/" + createdBook.getId() + "/publish", null, Book.class);

        Book retrievedBook = this.restTemplate.getForObject(booksURL + "/" + createdBook.getId(), Book.class);

        assertThat(retrievedBook.isPublished()).isTrue();
    }
}
