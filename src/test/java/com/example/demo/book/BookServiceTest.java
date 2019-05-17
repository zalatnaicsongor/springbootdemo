package com.example.demo.book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookServiceTest {
    @Mock
    private UUIDGenerator uuidGenerator;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private CurrentTimeGenerator currentTimeGenerator;

    private BookService bookService;

    private UUID superRandomUUID = UUID.fromString("55a8828e-eee6-4f77-9728-db40157fddd1");

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository, uuidGenerator, rabbitTemplate, currentTimeGenerator);
        when(uuidGenerator.newUUID()).thenReturn(superRandomUUID);
        when(currentTimeGenerator.getCurrentTime()).thenReturn(Instant.ofEpochSecond(5));
    }

    @Nested
    @DisplayName("Save book")
    //this is like 'describe()' in js
    public class SaveBook {
        @Test
        @DisplayName("It saves the book")
        //this is like 'it()' in js
        public void itSavesTheBook() {
            SaveBookRequest saveBookRequest = new SaveBookRequest("author", "title");

            bookService.saveBook(saveBookRequest);

            Book expectedBook = new Book(superRandomUUID, "author", "title");
            verify(bookRepository).save(expectedBook);
        }
    }

    @Nested
    @DisplayName("Get book by id")
    public class GetBookById {
        private UUID existingId = UUID.fromString("2c570820-89f1-422c-9415-909c2d4c8d05");
        private UUID notFoundId = UUID.fromString("0d14fb61-f723-4527-9bf8-4af6418efbff");

        private Book book = new Book(existingId, "author", "title");


        @BeforeEach
        public void setUp() {
            when(bookRepository.findById(existingId)).thenReturn(Optional.of(book));
            when(bookRepository.findById(notFoundId)).thenReturn(Optional.empty());
        }

        @Test
        @DisplayName("It gets the book by id")
        public void itGetsTheBookById() {
            assertThat(bookService.getBookById(existingId)).isEqualTo(book);
        }

        @Test
        @DisplayName("It throws an exception when the book is not found")
        public void itThrowsAnExceptionWhenTheBookIsNotFound() {
            assertThrows(NoSuchElementException.class, () -> bookService.getBookById(notFoundId));
        }
    }

    @Nested
    @DisplayName("Publish book by id")
    public class PublishBook {

        private UUID existingId = UUID.fromString("2c570820-89f1-422c-9415-909c2d4c8d05");
        private UUID notFoundId = UUID.fromString("0d14fb61-f723-4527-9bf8-4af6418efbff");

        private Book book = new Book(existingId, "author", "title");


        @BeforeEach
        public void setUp() {
            when(bookRepository.findById(existingId)).thenReturn(Optional.of(book));
            when(bookRepository.findById(notFoundId)).thenReturn(Optional.empty());
        }

        @Test
        @DisplayName("It gets the book by id")
        public void itGetsTheBookById() {
            assertThat(bookService.publishBookById(existingId)).isEqualTo(book);
        }

        @Test
        @DisplayName("It throws an exception when the book is not found")
        public void itThrowsAnExceptionWhenTheBookIsNotFound() {
            assertThrows(NoSuchElementException.class, () -> bookService.publishBookById(notFoundId));
        }

        @Test
        @DisplayName("It marks the book as published")
        public void itMarksTheBookAsPublished() {
            assertThat(bookService.publishBookById(existingId).isPublished()).isTrue();
        }

        @Test
        @DisplayName("It publishes a BookPublishedEvent message with the id and the current timestamp")
        public void itPublishesAMessage() {
            bookService.publishBookById(existingId);
            verify(rabbitTemplate).convertAndSend(new BookPublishedEvent(existingId, Instant.ofEpochSecond(5)));
        }
    }
}