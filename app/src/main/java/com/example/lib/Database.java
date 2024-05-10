package com.example.lib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.libralib.models.Book;
import com.example.libralib.models.Member;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create Book table
        String qr1 = "CREATE TABLE Book(\n" +
                "    BOOK_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    TITLE TEXT,\n" +
                "    PUBLISHER_NAME TEXT,\n" +
                "    AVAILABLE INTEGER DEFAULT 1\n" +
                ")";
        sqLiteDatabase.execSQL(qr1);

        // create Publisher table
        String qr2 = "CREATE TABLE Publisher(\n" +
                "NAME VARCHAR(20),\n" +
                "ADDRESS VARCHAR(30),\n" +
                "PHONE VARCHAR(10),\n" +
                "PRIMARY KEY (NAME));\n";
        sqLiteDatabase.execSQL(qr2);

        // create Branch table
        String qr3 = "CREATE TABLE Branch(\n" +
                "BRANCH_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "BRANCH_NAME TEXT,\n" +
                "ADDRESS TEXT);\n";
        sqLiteDatabase.execSQL(qr3);

        // create Member table
        String qr4 = "CREATE TABLE Member(\n" +
                "CARD_NO INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "NAME TEXT,\n" +
                "ADDRESS TEXT,\n" +
                "PHONE TEXT,\n" +
                "UNPAID_DUES REAL DEFAULT 0);\n";
        sqLiteDatabase.execSQL(qr4);

        // create Book_Author table
        String qr5 = "CREATE TABLE Book_Author(\n" +
                "BOOK_ID INTEGER,\n" +
                "AUTHOR_NAME TEXT,\n" +
                "PRIMARY KEY(BOOK_ID, AUTHOR_NAME),\n" +
                "FOREIGN KEY(BOOK_ID) REFERENCES Book);\n";
        sqLiteDatabase.execSQL(qr5);

        // create Book_Copy table
        String qr6 = "CREATE TABLE Book_Copy(\n" +
                "BOOK_ID INTEGER,\n" +
                "BRANCH_ID INTEGER,\n" +
                "ACCESS_NO INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "FOREIGN KEY(BOOK_ID) REFERENCES Book(BOOK_ID),\n" +
                "FOREIGN KEY(BRANCH_ID) REFERENCES Branch(BRANCH_ID));\n";
        sqLiteDatabase.execSQL(qr6);

        // create Book_Loan table
        String qr7 = "CREATE TABLE Book_Loan(\n" +
                "ACCESS_NO INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "BRANCH_ID INTEGER,\n" +
                "CARD_NO INTEGER,\n" +
                "DATE_OUT TEXT,\n" +
                "DATE_DUE TEXT,\n" +
                "DATE_RETURNED TEXT,\n" +
                "FOREIGN KEY(ACCESS_NO, BRANCH_ID) REFERENCES Book_Copy,\n" +
                "FOREIGN KEY(CARD_NO) REFERENCES Member,\n" +
                "FOREIGN KEY(BRANCH_ID) REFERENCES Branch);\n";
        sqLiteDatabase.execSQL(qr7);

        String qr8 = "CREATE TABLE users(\n" +
                "username VARCHAR(20),\n" +
                "email VARCHAR(30),\n" +
                "password VARCHAR(20),\n" +
                "PRIMARY KEY (username));\n";
        sqLiteDatabase.execSQL(qr8);

        com.example.libralib.Initial.InitialData.insertInitialMember(sqLiteDatabase);
        com.example.libralib.Initial.InitialData.insertInitialPublisher(sqLiteDatabase);
        com.example.libralib.Initial.InitialData.insertInitialBook(sqLiteDatabase);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void register(String username, String email, String Password){
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", Password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }

    public int login(String email, String password){
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
        if(c.moveToFirst()){
            result = 1;
        }
        c.close();
        return result;
    }

    public List<String> getPublishers(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM Publisher", null);

        // Return the mock data directly
        List<String> list = Arrays.asList();
        ArrayList<String> spinnerItems = new ArrayList<>(list);
        while (cursor.moveToNext()) {
            spinnerItems.add(cursor.getString(0));
        }
        cursor.close();
        return spinnerItems;
    }

    public int addBook(String title, String authorName,String publisher){
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("publisher_name", publisher);
        SQLiteDatabase db = getWritableDatabase();
        long newRowId = db.insert("Book", null, cv);
        if(newRowId == -1) {
            // Error
            return 1;
        } else {
            // Inserted successfully
        }
        ContentValues author = new ContentValues();
        author.put("book_id", newRowId);
        author.put("author_name", authorName);
        db.insert("Book_Author", null, author);
        db.close();
        return 0;
    }

    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        String q = "SELECT BOOK_ID,TITLE,AVAILABLE FROM Book";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(q, null);
        while (c.moveToNext()) {
            int bookId = c.getInt(0);
            String title = c.getString(1);
            int available = c.getInt(2);
            books.add(new Book(bookId, title, available));
        }
        return books;
    }

    public int addMember(String name, String address, String phone){
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("address", address);
        cv.put("phone", phone);
        SQLiteDatabase db = getWritableDatabase();
        long newRowId = db.insert("Member", null, cv);
        if(newRowId == -1) {
            // Error
            return 1;
        } else {
            // Inserted successfully
        }
        db.close();
        return 0;
    }

    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        String q = "SELECT CARD_NO,NAME,ADDRESS,PHONE FROM Member";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(q, null);
        while (c.moveToNext()) {
            int cardNo = c.getInt(0);
            String name = c.getString(1);
            String address = c.getString(2);
            String phone = c.getString(3);
            members.add(new Member(cardNo, name, address, phone));
        }
        return members;
    }
}
