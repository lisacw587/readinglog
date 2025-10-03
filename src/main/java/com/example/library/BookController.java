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

        // save book to DB (status default is to "to-read" in Book entity)
        bookRepository.save(new Book(title, author, year));

        // Refresh from DB
        model.addAttribute("books", bookRepository.findAll());

        //points to index.html
        return "index"; 
    }

    // POST route to delete a book
    @PostMapping("/books/delete")
    public String deleteBook(@RequestParam Long id, Model model) {
        // Delete book by ID
        bookRepository.deleteById(id);

        // Refresh from DB
        model.addAttribute("books", bookRepository.findAll());


        // points to index.html
        return "index"; 
    }


    // update status (reading, read, to-read)
    @PostMapping("/books/status")
    public String updateBookStatus(@RequestParam Long id, @RequestParam String status, Model model) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book != null) {
            // update field
            book.setStatus(status);
            bookRepository.save(book);
        }

        // refresh book list
        model.addAttribute("books", bookRepository.findAll());

        // back to index.html
        return "index";
    }


    @GetMapping("/reading")
    public String getReadingBooks(Model model) {
        model.addAttribute("books", bookRepository.findByStatus("reading"));
        // load reading.html
        return "reading";
    }


    @GetMapping("/read")
    public String getReadBooks(Model model) {
        model.addAttribute("books", bookRepository.findByStatus("read"));
        // loads read.html
        return "read"; 
    }

}