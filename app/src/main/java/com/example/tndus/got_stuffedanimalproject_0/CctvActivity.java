package com.example.tndus.got_stuffedanimalproject_0;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CctvActivity extends AppCompatActivity {
String yearStr;
String monthStr;
String dayStr;
String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        CalendarView calendar = (CalendarView)findViewById(R.id.calendarView);
        Button selectButton = (Button)findViewById(R.id.selectButton);

        //날짜가 선택되기 전 오늘 날짜 가져와서 date에 저장
        long now = System.currentTimeMillis();
        Date nowDate = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        date = sdf.format(nowDate);
        Toast.makeText(CctvActivity.this,date,Toast.LENGTH_SHORT).show();


        //날짜가 변경될 때 이벤트를 받기 위한 리스너
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            //날짜가 선택될 때 리스너
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(CctvActivity.this, year + "/" + (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();

                yearStr= String.valueOf(year);
                monthStr=String.valueOf(month+1);
                dayStr=String.valueOf(dayOfMonth);

                if((month+1)<=9){
                    monthStr="0"+monthStr;
                }
                if(dayOfMonth<=9){
                    dayStr="0"+dayStr;
                }

                date = yearStr+monthStr+dayStr;
                //Toast.makeText(CctvActivity.this,date,Toast.LENGTH_SHORT).show();


            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final String userID = intent.getStringExtra("userID");
                Intent dateIntent = new Intent(CctvActivity.this,CctvListTestActivity.class);
                dateIntent.putExtra("date",date);
                dateIntent.putExtra("userID",userID);
                //Log.d("cctvlogogggggg",userID);
                CctvActivity.this.startActivity(dateIntent);
            }
        });
    }
}