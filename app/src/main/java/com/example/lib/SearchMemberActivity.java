package com.example.lib;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libralib.models.Member;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnPxWidthModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class SearchMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_member);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        String dynamicString = "Search Member";
        toolbarTitle.setText(dynamicString);

        // Get a reference to the TableView
        TableView<String[]> tableView = findViewById(R.id.MemtableView);
        tableView.setColumnCount(4);

        // set table column weight
        TableColumnPxWidthModel columnModel = new TableColumnPxWidthModel(4, 350);
        columnModel.setColumnWidth(0, 100);
        columnModel.setColumnWidth(1, 250);
        columnModel.setColumnWidth(2, 400);
        columnModel.setColumnWidth(3, 350);
        tableView.setColumnModel(columnModel);

        // Set up the header adapter
        String[] headers = {"id","Name", "Address", "Phone"};
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, headers));
        Database db = new Database(getApplicationContext(),"libralib",null,1);
        List<Member> members = db.getMembers();

        // Convert the List<Member> to a 2D String array
        String[][] MemberData = new String[members.size()][4];
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            MemberData[i][0] = String.valueOf(member.memberId);
            MemberData[i][1] = member.name;
            MemberData[i][2] = member.address;
            MemberData[i][3] = member.phone;
        }
        // set the data adapter
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, MemberData));
        EditText memSearch = findViewById(R.id.editTextMemSearch);
        memSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Code to execute when the text is changing
                List<Member> filteredMembers = new ArrayList<>();

                for (Member member : members) {
                    if (member.name.toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredMembers.add(member);
                    }
                }

                // Convert the filtered book data to a 2D String array
                String[][] filteredMemberData = new String[filteredMembers.size()][3];
                for (int i = 0; i < filteredMembers.size(); i++) {
                    Member member = filteredMembers.get(i);
                    filteredMemberData[i][0] = String.valueOf(member.memberId);
                    filteredMemberData[i][1] = member.name;
                    filteredMemberData[i][2] = member.address;
                    filteredMemberData[i][3] = member.phone;
                }
                // Set up the data adapter
                tableView.setDataAdapter(new SimpleTableDataAdapter(SearchMemberActivity.this, filteredMemberData));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}