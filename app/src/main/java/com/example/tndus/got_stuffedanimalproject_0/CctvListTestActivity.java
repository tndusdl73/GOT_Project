package com.example.tndus.got_stuffedanimalproject_0;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CctvListTestActivity extends AppCompatActivity{

    private static String TAG = "phpquerytest";

    private static final String TAG_JSON = "tndus";
    private static final String TAG_INDEX = "index";
    private static final String TAG_TITLE = "title";
    private static final String TAG_FILENAME = "fileName";

    String userID;
    String day;
    String intentFilename;

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mListViewList;
    TextView mTextViewLabel;
    //EditText mEditTextSearchKeyword;

    String mJsonString;
    String num;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        day = intent.getStringExtra("date");

        Log.d("show me the userID",userID);
        Log.d("show me the day",day);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv_list_test);

        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mListViewList = (ListView) findViewById(R.id.listView_main_list);
        mTextViewLabel = (TextView)findViewById(R.id.textView_main_label);
        //mEditTextSearchKeyword = (EditText) findViewById(R.id.editText_main_searchKeyword);
        mTextViewLabel.setText(userID+"님 \n" +
                day.substring(0, 4) + "/" +
                day.substring(4, 6) + "/" +
                day.substring(6, 8)+"일의 영상 목록");



//        mArrayList.clear();
        GetData task = new GetData();
        task.execute(userID);

        mArrayList = new ArrayList<>();

        //리스트뷰 선택했을때 이벤트처리
        mListViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                num=String.valueOf(position);   //1부터 시작
                //Log.d("position",String.valueOf(num));

                Intent intent = new Intent(getApplicationContext(),PlayCctvActivity.class);
                intent.putExtra("fileName",intentFilename);
                intent.putExtra("index",num);
                Log.d("what is filename",intentFilename);
                startActivity(intent);
            }
        });
    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(CctvListTestActivity.this,

                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null) {
                mTextViewResult.setText(errorString);

            } else {
                mJsonString = result;
                showResult();
                Log.d("ddddd","3444443425263777");
            }
        }

        @Override

        protected String doInBackground(String... params) {
            //String searchKeyword = params[0];

            String searchKeyword = userID;
            String searchKeyword2 = day;
            String serverURL = "http://tndusdl73.cafe24.com/getVideoList.php";

            String postParameters = "userID=" + searchKeyword + "&" + "day=" + searchKeyword2 ;
            String postParameters2 = "day="+searchKeyword2;

            Log.d("postParameters",postParameters);

            try {

                Log.d("fffff","7777");
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                Log.d("dataBefore","333333");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                Log.d("dataAfter","22222222");
                int responseStatusCode = httpURLConnection.getResponseCode();


                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;


                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                Log.d("good","333");
                return sb.toString().trim();

            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }
    }

    private void showResult() {

        Log.d("check","eee");
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);


            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put(TAG_INDEX, "번호");
            hashMap.put(TAG_TITLE, "제목");
            hashMap.put(TAG_FILENAME, "파일명");

            mArrayList.add(hashMap);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String index = item.getString(TAG_INDEX);
                String title = item.getString(TAG_TITLE);
                String fileName = item.getString(TAG_FILENAME);


                hashMap = new HashMap<>();
                Log.d("index",index);
                intentFilename = fileName;


                hashMap.put(TAG_INDEX, index);
                hashMap.put(TAG_TITLE, title);
                hashMap.put(TAG_FILENAME, fileName);

                mArrayList.add(hashMap);

                if(index.equals("-1")){
                    mListViewList.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "영상이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            ListAdapter adapter = new SimpleAdapter(
                    CctvListTestActivity.this, mArrayList, R.layout.item_list,
                    new String[]{TAG_INDEX, TAG_TITLE, TAG_FILENAME},
                    new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_address}
            );

            mListViewList.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }
}