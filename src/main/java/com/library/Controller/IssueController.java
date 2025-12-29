package com.library.Controller;

import com.library.Entity.IssueRecord;
import com.library.Service.IssueRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issuerecords")
public class IssueController {

    @Autowired
    private IssueRecordService issueRecordService;

    @PostMapping("/issuebook/{bookId}")
    public ResponseEntity<IssueRecord> issueBook(@PathVariable Long bookId){
        return ResponseEntity.ok(issueRecordService.issueBook(bookId));
    }

    @PostMapping("/returnbook/{issueRecordId}")
    public ResponseEntity<IssueRecord> returnBook(@PathVariable Long issueRecordId){
        return ResponseEntity.ok(issueRecordService.returnBook(issueRecordId));
    }
}
