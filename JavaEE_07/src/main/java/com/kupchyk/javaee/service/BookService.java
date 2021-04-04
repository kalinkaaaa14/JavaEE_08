package com.kupchyk.javaee.service;

import com.kupchyk.javaee.dto.BookDto;
import com.kupchyk.javaee.model.Book;
import com.kupchyk.javaee.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public List<String> createNewBook(final BookDto newBook) {
        List<String> errors = new ArrayList<>();

        Optional<Book> b = bookRepository.findByIsbn(newBook.getIsbn());

        if (b.isPresent()) {
            errors.add("Isbn is taken. Try again");
        }

        log.info("Try to create new book: {}", newBook.getIsbn());
        Book book = Book.builder()
                .isbn(newBook.getIsbn())
                .title(newBook.getTitle())
                .author(newBook.getAuthor())
                .build();
        if(errors.isEmpty()){
            final Book bookAdded = bookRepository.save(book);
            log.info("New book is created: {}", bookAdded);
        }
        return errors;
    }

    @Transactional
    public List<Book> returnAllBooks(){
        return  bookRepository.findAll();
    }

    @Transactional
    public List<Book> findBooks(final String s){
        return bookRepository.findAllWhereTitleLikeOrAuthorLikeOrIsbnLike(s);
    }

    @Transactional
    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

}

