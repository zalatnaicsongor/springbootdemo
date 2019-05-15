package com.example.demo.book;

import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UUIDGenerator uuidGenerator;

    public BookService(BookRepository bookRepository, UUIDGenerator uuidGenerator) {
        this.bookRepository = bookRepository;
        this.uuidGenerator = uuidGenerator;
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
}
