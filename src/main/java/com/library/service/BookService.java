package com.library.service;

import com.library.entity.Book;
import com.library.exceptions.NotFoundException;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository repository;

    public List<Book> getAllBooks(){
        return repository.findAll();
    }

    public Book insetBook(Book book){
        return repository.save(book);
    }

    public void deleteBookById(Long id){
        repository.deleteById(id);
    }

    public Book updateBook(Book book, Long id){
        Book bookFromBd = repository.findById(id).orElseThrow(NotFoundException :: new);
        bookFromBd.setAuthor(book.getAuthor());
        bookFromBd.setName(book.getName());
        bookFromBd.setCategory(book.getCategory());
        bookFromBd.setYear(book.getYear());
        return repository.save(bookFromBd);
    }

    public Book getBookById(Long id){
        return repository.findById(id).orElseThrow(NotFoundException :: new);
    }


}
