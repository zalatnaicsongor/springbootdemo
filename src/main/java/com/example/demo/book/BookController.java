package com.example.demo.book;

import com.example.demo.book.configuration.DummyConfiguration;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ApiOperation(value = "Creates a book")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Book was created ")
    })
    @ResponseStatus(value= HttpStatus.CREATED)
    public ResponseEntity<Book> saveBook(@RequestBody @Valid SaveBookRequest request) {
        Book book = bookService.saveBook(request);
        return ResponseEntity.created(URI.create("/books/" + book.getId())).body(book);
    }

    @GetMapping(value = "/{id}")
    public Book getBookById(@PathVariable UUID id) {
        return bookService.getBookById(id);
    }

    @PostMapping(value = "/{id}/publish")
    public void publishBook(@PathVariable UUID id) {
        bookService.publishBookById(id);
    }
}
