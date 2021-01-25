package com.library.service;

import com.library.entity.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LibraryServiceTest {

    @TestConfiguration
    static class LibraryConfiguration {
        @Bean
        public BookService bookServiceTest(){
            return new BookService();
        }
    }

    @Autowired
    public BookService bookService;

    @Test
    public void addBook(){
        Book newBook = insertOneBook();

        Assert.assertEquals(newBook.getName(), "Book 1");
        Assert.assertEquals(newBook.getAuthor(), "author 1");
    }

    @Test
    public void getBook(){
        Book newBook = insertOneBook();

        Book getBook = bookService.getBookById(newBook.getId());

        Assert.assertEquals(getBook.getName(), "Book 1");
        Assert.assertEquals(getBook.getAuthor(), "author 1");
    }

    @Test
    public void updateBook(){
        Book newBook = insertOneBook();

        Book bookUpdate = new Book();
        bookUpdate.setName("Book Update");
        bookUpdate.setAuthor("author Update");
        bookUpdate.setYear(1995);
        bookUpdate.setCategory("category Update");

        Book upBook = bookService.updateBook(bookUpdate, newBook.getId());

        Assert.assertEquals(upBook.getName(), "Book Update");
        Assert.assertEquals(upBook.getAuthor(), "author Update");
    }

    @Test
    public void deleteBook(){
        Book newBook = insertOneBook();

        bookService.deleteBookById(newBook.getId());
    }

    private Book insertOneBook(){
        Book addBook = new Book();
        addBook.setName("Book 1");
        addBook.setAuthor("author 1");
        addBook.setYear(1994);
        addBook.setCategory("category 1");

        return bookService.insetBook(addBook);
    }


}
