package com.example.lib;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libralib.models.Book;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnPxWidthModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class SearchBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        String dynamicString = "Search Book";
        toolbarTitle.setText(dynamicString);

        // Get a reference to the TableView
        TableView<String[]> tableView = findViewById(R.id.tableView);
        tableView.setColumnCount(3);

        // set table column weight
        TableColumnPxWidthModel columnModel = new TableColumnPxWidthModel(4, 350);
        columnModel.setColumnWidth(0, 250);
        columnModel.setColumnWidth(1, 650);
        columnModel.setColumnWidth(2, 200);
        tableView.setColumnModel(columnModel);

// Set up the header adapter
        String[] headers = {"Id", "Name", "Avail"};
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, headers));
        Database db = new Database(getApplicationContext(),"libralib",null,1);
        List<Book> books = db.getBooks();


// Convert the List<Book> to a 2D String array
        String[][] bookData = new String[books.size()][3];
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            bookData[i][0] = String.valueOf(book.bookId);
            bookData[i][1] = book.title;
            bookData[i][2] = String.valueOf(book.available);
        }
// Set up the data adapter
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, bookData));
    EditText editTextSearch = findViewById(R.id.editTextSearch);

    editTextSearch.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Code to execute before the text is changed
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Code to execute when the text is changing

            List<Book> filteredBooks = new ArrayList<>();
            for (Book book : books) {
                if (book.title.toLowerCase().contains(s.toString().toLowerCase())) {
                    filteredBooks.add(book);
                }
            }

            // Convert the filtered book data to a 2D String array
            String[][] filteredBookData = new String[filteredBooks.size()][3];
            for (int i = 0; i < filteredBooks.size(); i++) {
                Book book = filteredBooks.get(i);
                filteredBookData[i][0] = String.valueOf(book.bookId);
                filteredBookData[i][1] = book.title;
                filteredBookData[i][2] = String.valueOf(book.available);
            }

            // Update the TableView with the filtered book data
            tableView.setDataAdapter(new SimpleTableDataAdapter(SearchBookActivity.this, filteredBookData));
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Code to execute after the text has changed
        }
    });
    }


}