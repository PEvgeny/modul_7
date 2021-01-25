package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entity.Book;
import com.library.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void addBook() throws Exception{
        Book book = new Book();
        book.setName("Book 1");
        book.setAuthor("author 1");
        book.setYear(1994);
        book.setCategory("category 1");

        mockMvc.perform(
            post("/books")
                .content(objectMapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void updateBook() throws Exception{
        Book bookOriginal = new Book();
        bookOriginal.setName("Book 1");
        bookOriginal.setAuthor("author 1");
        bookOriginal.setYear(1994);
        bookOriginal.setCategory("category 1");

        Mockito.when(bookService.updateBook(Mockito.any(), Mockito.anyLong())).thenReturn(bookOriginal);

        mockMvc.perform(
            put("/books/1")
                .content(objectMapper.writeValueAsString(bookOriginal))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andExpect(jsonPath("name").value("Book 1"));

    }

    @Test
    public void getBook() throws Exception{
        Book book = new Book();
        book.setName("Book 1");
        book.setAuthor("author 1");
        book.setYear(1994);
        book.setCategory("category 1");

        Mockito.when(bookService.getAllBooks())
            .thenReturn(Collections.singletonList(book));

        mockMvc.perform(
            get("/books")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Book 1"))
            .andExpect(jsonPath("$[0].author").value("author 1"))
            .andExpect(jsonPath("$[0].year").value(1994))
            .andExpect(jsonPath("$[0].category").value("category 1"));
    }


}
