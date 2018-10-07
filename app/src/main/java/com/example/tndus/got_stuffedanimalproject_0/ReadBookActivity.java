package com.example.tndus.got_stuffedanimalproject_0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReadBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        Button bookListButton = (Button)findViewById(R.id.bookListButton);
        Button bookDownloadButton = (Button)findViewById(R.id.bookDownloadButton);
        Button bookRecordButton = (Button)findViewById(R.id.bookRecordButton);

        bookListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookListIntent = new Intent(ReadBookActivity.this, BookListActivity.class);
                ReadBookActivity.this.startActivity(bookListIntent);
            }
        });

        bookDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookDownloadIntent = new Intent(ReadBookActivity.this, BookDownloadActivity.class);
                ReadBookActivity.this.startActivity(bookDownloadIntent);
            }
        });
        bookRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookRecordIntent = new Intent(ReadBookActivity.this,AudioRecordActivity.class);
                ReadBookActivity.this.startActivity(bookRecordIntent);
            }
        });



    }
}
