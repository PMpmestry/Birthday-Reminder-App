package com.example.prathamesh1.lavanya_makeup;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Birthday extends AppCompatActivity {
    List birthdayArr;
    Cursor c = null;
    ListView birthdayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        birthdayList = (ListView) findViewById(R.id.birthdayList);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        dataBaseHelper.openDB();
        c = dataBaseHelper.getBirthdays();
        birthdayArr = new ArrayList<>();
        while (c.moveToNext()) {
            birthdayArr.add(c.getString(c.getColumnIndex(DataBaseHelper.KEY_NAME)));
        }
        ArrayAdapter birthdayAdapter = new ArrayAdapter<>(this, R.layout.birthday_list_item, R.id.Name, birthdayArr);

        birthdayList.setAdapter(birthdayAdapter);
    }
}
