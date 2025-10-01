package com.example.library;

import org.springframework.data.jpa.repository.JpaRepository;

// Repository interface for Book entity
public interface BookRepository extends JpaRepository<Book, Long> {
}
