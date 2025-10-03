package com.example.library;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repository interface for Book entity
public interface BookRepository extends JpaRepository<Book, Long> {
    // custom query methods
    List<Book> findByStatus(String status);
}
