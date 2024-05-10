package com.example.lib.models;

public class Book {
    public int bookId;
    public String title;
    public int available;

    public Book(int bookId, String title, int available) {
        this.bookId = bookId;
        this.title = title;
        this.available = available;
    }

    // getters and setters
}