package com.example.demo.book;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, UUID> {
}