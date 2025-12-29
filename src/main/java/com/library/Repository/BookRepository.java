package com.library.Repository;

import com.library.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book getBookById(Long id);

    void deleteBookById(Long id);
}
