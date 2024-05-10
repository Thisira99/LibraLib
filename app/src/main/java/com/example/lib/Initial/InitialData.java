package com.example.lib.Initial;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class InitialData {
    public static void insertInitialPublisher(SQLiteDatabase sqLiteDatabase){
        insertPublisher(sqLiteDatabase, "Penguin", "Penguin Address", "1234567890");
        insertPublisher(sqLiteDatabase, "Harper Collins", "Harper Collins Address", "1234567890");
        insertPublisher(sqLiteDatabase, "Random House", "Random House Address", "1234567890");
        insertPublisher(sqLiteDatabase, "Simon & Schuster", "Simon & Schuster Address", "1234567890");
        insertPublisher(sqLiteDatabase, "Macmillan", "Macmillan Address", "1234567890");
        insertPublisher(sqLiteDatabase, "Hachette", "Hachette Address", "1234567890");
        insertPublisher(sqLiteDatabase, "Harlequin", "Harlequin Address", "1234567890");
        insertPublisher(sqLiteDatabase, "Scholastic", "Scholastic Address", "1234567890");
    }
    private static void insertPublisher(SQLiteDatabase sqLiteDatabase, String name, String address, String phone) {
        ContentValues publisher = new ContentValues();
        publisher.put("NAME", name);
        publisher.put("ADDRESS", address);
        publisher.put("PHONE", phone);
        sqLiteDatabase.insert("Publisher", null, publisher);
    }

    public static void insertInitialBook(SQLiteDatabase sqLiteDatabase){
        insertBook(sqLiteDatabase, "The Alchemist", "Paulo Coelho", "Penguin", 1);
        insertBook(sqLiteDatabase, "The Da Vinci Code", "Dan Brown", "Harper Collins", 1);
        insertBook(sqLiteDatabase, "The Catcher in the Rye", "J.D. Salinger", "Random House", 1);
        insertBook(sqLiteDatabase, "The Great Gatsby", "F. Scott Fitzgerald", "Simon & Schuster", 1);
        insertBook(sqLiteDatabase, "To Kill a Mockingbird", "Harper Lee", "Macmillan", 0);
        insertBook(sqLiteDatabase, "1984", "George Orwell", "Hachette", 1);
        insertBook(sqLiteDatabase, "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Scholastic", 1);
        insertBook(sqLiteDatabase, "The Hunger Games", "Suzanne Collins", "Harlequin", 0);
        insertBook(sqLiteDatabase, "The Hobbit", "J.R.R. Tolkien", "Scholastic", 1);
        insertBook(sqLiteDatabase, "The Lord of the Rings", "J.R.R. Tolkien", "Scholastic", 1);
    }
    private static void insertBook(SQLiteDatabase sqLiteDatabase, String title, String author_, String publisher, int available) {
        ContentValues book = new ContentValues();
        book.put("TITLE", title);
        book.put("PUBLISHER_NAME", publisher);
        book.put("AVAILABLE", available);
        long newRowId = sqLiteDatabase.insert("Book", null, book);
        if(newRowId != -1){
            ContentValues author = new ContentValues();
            author.put("BOOK_ID", newRowId);
            author.put("AUTHOR_NAME", author_);
            sqLiteDatabase.insert("Author", null, author);
        }
    }

    public static void insertInitialMember(SQLiteDatabase sqLiteDatabase){
        insertMember(sqLiteDatabase, "John Doe", "John Doe Address", "1234567890");
        insertMember(sqLiteDatabase, "Jane Doe", "Jane Doe Address", "1234567890");
        insertMember(sqLiteDatabase, "Alice", "Alice Address", "1234567890");
        insertMember(sqLiteDatabase, "Bob", "Bob Address", "1234567890");
        insertMember(sqLiteDatabase, "Charlie", "Charlie Address", "1234567890");
        insertMember(sqLiteDatabase, "David", "David Address", "1234567890");
        insertMember(sqLiteDatabase, "Eve", "Eve Address", "1234567890");
        insertMember(sqLiteDatabase, "Frank", "Frank Address", "1234567890");
        insertMember(sqLiteDatabase, "Grace", "Grace Address", "1234567890");
        insertMember(sqLiteDatabase, "Heidi", "Heidi Address", "1234567890");
    }

    private static void insertMember(SQLiteDatabase sqLiteDatabase, String name, String address, String phone) {
        ContentValues member = new ContentValues();
        member.put("NAME", name);
        member.put("ADDRESS", address);
        member.put("PHONE", phone);
        sqLiteDatabase.insert("Member", null, member);
    }

}
