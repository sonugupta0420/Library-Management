package com.library.Service;

import com.library.Entity.Book;
import com.library.Entity.IssueRecord;
import com.library.Entity.User;
import com.library.Repository.BookRepository;
import com.library.Repository.IssueRecordRepository;
import com.library.Repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class IssueRecordService {

    @Autowired
    private IssueRecordRepository issueRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public IssueRecord issueBook(Long bookId) {
        Book book = bookRepository.getBookById(bookId);
        if (book.getQuantity()<=0 || book.getIsAvailable()){
            throw new RuntimeException("Book is not available");
        }

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("User not found"));

        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setBook(book);
        issueRecord.setUser(user);
        issueRecord.setIssueDate(java.time.LocalDate.now());
        issueRecord.setDueDate(java.time.LocalDate.now().plusDays(14));
        issueRecord.setIsReturned(false);

        book.setQuantity(book.getQuantity()-1);
        if (book.getQuantity()==0){
            book.setIsAvailable(false);
        }
        bookRepository.save(book);

        return issueRecordRepository.save(issueRecord);
    }

    public IssueRecord returnBook(Long issueRecordId) {
        IssueRecord issueRecord = issueRecordRepository.findById(issueRecordId)
                .orElseThrow(() -> new RuntimeException("Issue record not found"));

        if (issueRecord.getIsReturned()) {
            throw new RuntimeException("Book already returned");
        }

        issueRecord.setReturnDate(java.time.LocalDate.now());
        issueRecord.setIsReturned(true);

        Book book = issueRecord.getBook();
        book.setQuantity(book.getQuantity() + 1);
        book.setIsAvailable(true);
        bookRepository.save(book);

        return issueRecordRepository.save(issueRecord);
    }
}
