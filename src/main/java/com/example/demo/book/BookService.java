package com.example.demo.book;

import io.micrometer.core.instrument.Counter;
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
    private final Counter numberOfGets;

    public BookService(BookRepository bookRepository, UUIDGenerator uuidGenerator, RabbitTemplate rabbitTemplate,
                       CurrentTimeGenerator currentTimeGenerator, Counter numberOfGets) {
        this.bookRepository = bookRepository;
        this.uuidGenerator = uuidGenerator;
        this.rabbitTemplate = rabbitTemplate;
        this.numberOfGets = numberOfGets;
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
        numberOfGets.increment();
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
