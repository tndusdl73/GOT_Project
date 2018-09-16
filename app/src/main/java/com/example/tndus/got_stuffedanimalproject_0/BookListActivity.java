package com.example.tndus.got_stuffedanimalproject_0;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    List<String> list;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);


        list = new ArrayList<>();

        Field[] fields = R.raw.class.getFields();
        for(int i=0; i<fields.length; i++){
            list.add(fields[i].getName());
        }

        //remove first two elements
        list.remove(0);
        list.remove(0);

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        ListView listView = (ListView) findViewById(R.id.bookListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (mediaPlayer != null){
                            mediaPlayer.release();
                        }

                        int reaID = getResources().getIdentifier(list.get(position),"raw",getPackageName());
                        mediaPlayer = MediaPlayer.create(BookListActivity.this,reaID);
                        mediaPlayer.start();
                    }
                }
        );
    }
}
