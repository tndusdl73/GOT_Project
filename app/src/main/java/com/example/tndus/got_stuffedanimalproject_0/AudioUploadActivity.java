package com.example.tndus.got_stuffedanimalproject_0;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class AudioUploadActivity extends Activity implements View.OnClickListener {

    Handler handler = new Handler();

    /*********  work only for Dedicated IP ***********/
    static final String FTP_HOST= "183.111.174.9";

    /*********  FTP USERNAME ***********/
    static final String FTP_USER = "tndusdl73";

    /*********  FTP PASSWORD ***********/
    static final String FTP_PASS  ="qlalf357";


    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};//권한 설정 변수
    private static final int MULTIPLE_PERMISSIONS = 101;//권한 동의 여부 문의 후 callback함수에 쓰일 변수

    Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_upload);

        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(this);

        //언제나 그랬듯이, 안드로이드 6 이상은 권한을 요구한다. 파일 입출력에 관한 권한을 런타임에 설정해야 함.
        if(Build.VERSION.SDK_INT>22){
            checkPermissions();
        }

    }

    //사용권한 묻는 함수
    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);//현재 컨텍스트가 pm 권한을 가졌는지 확인
            if (result != PackageManager.PERMISSION_GRANTED) {//사용자가 해당 권한을 가지고 있지 않을 경우
                permissionList.add(pm);//리스트에 해당 권한명을 추가한다
            }
        }
        if (!permissionList.isEmpty()) {//권한이 추가되었으면 해당 리스트가 empty가 아니므로, request 즉 권한을 요청한다.
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    //권한 요청의 콜백 함수. PERMISSION_GRANTED 로 권한을 획득하였는지를 확인할 수 있다.
    //아래에서는 !=를 사용했기에 권한 사용에 동의를 안했을 경우를 if문을 사용해서 코딩.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[0])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }

    //권한 획득에 동의하지 않았을 경우, 아래 메시지를 띄우며 해당 액티비티를 종료.
    private void showNoPermissionToastAndFinish() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    //버튼이 클릭되었을때의 이벤트, 즉 파일 전송 이벤트
    public void onClick(View v) {

        NThread nThread = new NThread();
        nThread.start();
    }

    //안드로이드 최근 버전에서는 네크워크 통신시에 반드시 스레드를 요구한다.
    class NThread extends Thread{
        public NThread() {
        }
        @Override

        public void run() {
            upload();
        }

        public void upload(){

            /********** Pick file from memory *******/
            //장치로부터 메모리 주소를 얻어낸 뒤, 파일명을 가지고 찾는다.
            //현재 이것은 내장메모리 루트폴더에 있는 것.
            //File f = new File(Environment.getExternalStorageDirectory()+"/book.mp4");
            File f = new File("sdcard/GOTBOOK/book.mp4");
            // Upload file
            uploadFile(f);
        }
    }

    public void uploadFile(File fileName){

        FTPClient client = new FTPClient();

        try {
            client.connect(FTP_HOST,21);//ftp 서버와 연결, 호스트와 포트를 기입
            Log.d("ddddd","dddddddd");
            client.login(FTP_USER, FTP_PASS);//로그인을 위해 아이디와 패스워드 기입
            client.setType(FTPClient.TYPE_BINARY);//2진으로 변경
            Log.d("tetsttttt","Etsetsttttt");
            client.changeDirectory("record/");//서버에서 넣고 싶은 파일 경로를 기입
            Log.d("1111111","11111111");
            client.upload(fileName, new MyTransferListener());//업로드 시작

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_SHORT).show();
                }
            });

            e.printStackTrace();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    /*******  Used to file upload and show progress  **********/

    public class MyTransferListener implements FTPDataTransferListener {

        public void started() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    btn.setVisibility(View.GONE);
                    // Transfer started
                    Toast.makeText(getBaseContext(), " Upload Started ...", Toast.LENGTH_SHORT).show();
                    //System.out.println(" Upload Started ...");
                }
            });
        }

        public void transferred(int length) {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    // Yet other length bytes has been transferred since the last time this
                    // method was called
                    Toast.makeText(getBaseContext(), " transferred ...", Toast.LENGTH_SHORT).show();
                    //System.out.println(" transferred ..." + length);
                }
            });
        }

        public void completed() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    btn.setVisibility(View.VISIBLE);
                    // Transfer completed

                    Toast.makeText(getBaseContext(), " completed ...", Toast.LENGTH_SHORT).show();
                    //System.out.println(" completed ..." );
                }
            });
        }

        public void aborted() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    btn.setVisibility(View.VISIBLE);
                    // Transfer aborted
                    Toast.makeText(getBaseContext(),"transfer aborted ,please try again...", Toast.LENGTH_SHORT).show();
                    //System.out.println(" aborted ..." );
                }
            });
        }

        public void failed() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    btn.setVisibility(View.VISIBLE);
                    // Transfer failed
                    System.out.println(" failed ..." );
                }
            });
        }

    }
}