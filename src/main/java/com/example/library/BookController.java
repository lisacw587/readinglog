package com.example.library;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {
    
    private final BookRepository bookRepository;

    // Connects controller to database through BookRepository
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // homepage route, redirect to /books
    @GetMapping("/")
    public String home() {
        return "redirect:/books";
    }

    // GET route to display books
    @GetMapping("/books")
    public String getBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll()); // fetch from database
        return "index";
    }

    // POST route to add a new book
    @PostMapping("/books")
    public String addBook(
        @RequestParam String title,
        @RequestParam String author,
        @RequestParam int year,
        Model model) {

        // save book to DB
        bookRepository.save(new Book(title, author, year));

        // Refresh from DB
        model.addAttribute("books", bookRepository.findAll());

        return "index"; // points to index.html
    }

    // POST route to delete a book
    @PostMapping("/books/delete")
    public String deleteBook(@RequestParam Long id, Model model) {
        // Delete book by ID
        bookRepository.deleteById(id);

        // Refresh from DB
        model.addAttribute("books", bookRepository.findAll());

        return "index"; // points to index.html
    }
}
