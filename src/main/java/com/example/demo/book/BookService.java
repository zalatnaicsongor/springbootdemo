package com.example.demo.book;

import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UUIDGenerator uuidGenerator;
    private final RabbitTemplate rabbitTemplate;
    private final CurrentTimeGenerator currentTimeGenerator;

    public BookService(BookRepository bookRepository, UUIDGenerator uuidGenerator, RabbitTemplate rabbitTemplate,
                       CurrentTimeGenerator currentTimeGenerator) {
        this.bookRepository = bookRepository;
        this.uuidGenerator = uuidGenerator;
        this.rabbitTemplate = rabbitTemplate;
        this.currentTimeGenerator = currentTimeGenerator;
    }

    @Transactional
    public Book saveBook(SaveBookRequest request) {
        Book book = new Book(uuidGenerator.newUUID(), request.getAuthor(), request.getTitle());

        bookRepository.save(book);

        return book;
    }

    @Transactional
    public Book getBookById(UUID id) {
        return bookRepository.findById(id).get();
    }

    @Transactional
    public Book publishBookById(UUID id) {
        Book book = getBookById(id);

        book.setPublished(true);

        rabbitTemplate.convertAndSend(new BookPublishedEvent(id, currentTimeGenerator.getCurrentTime()));

        return book;
    }
}
