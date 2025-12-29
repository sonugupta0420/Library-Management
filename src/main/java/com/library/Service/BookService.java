package com.library.Service;

import com.library.DTO.BookDTO;
import com.library.Entity.Book;
import com.library.Repository.BookRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.getBookById(id);
    }

    public Book addBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setQuantity(bookDTO.getQuantity());
        book.setIsAvailable(bookDTO.getIsAvailable());
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.getBookById(id);
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setQuantity(bookDTO.getQuantity());
        book.setIsAvailable(bookDTO.getIsAvailable());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteBookById(id);
    }
}
