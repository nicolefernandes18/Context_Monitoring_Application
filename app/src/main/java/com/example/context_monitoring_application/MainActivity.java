package com.example.context_monitoring_application;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    private Button measureHrtRate;

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private Button measureRespRate;
    private Uri videoUri;

    private String heartRate;
    private ExecutorService executorService;

    private ProgressBar progressHeartRate;

    private TextView heartRateValueText;

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button symBtn = (Button) findViewById(R.id.symBtn);

        heartRateValueText = findViewById(R.id.heartRateValueText);

        progressHeartRate = findViewById(R.id.progressBarHeartRate);

        executorService = Executors.newSingleThreadExecutor();

        symBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Symptoms.class));
            }
        });

        measureHrtRate = findViewById(R.id.heartRateBtn);
        measureRespRate = findViewById(R.id.respRateBtn);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null){
                Intent data = result.getData();
                videoUri = data.getData();
                calculateHeartRateValue(videoUri);
            }
        });

        measureHrtRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressHeartRate.setVisibility(View.VISIBLE);
                checkCameraPermissions();
            }
        });

        measureRespRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void checkCameraPermissions(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
        else{
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            activityResultLauncher.launch(intent);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQUEST_CAMERA_PERMISSION){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                activityResultLauncher.launch(intent);
            }
            else{
                Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String convertMediaUriToPath(Context context, Uri uri)
    {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    private void calculateHeartRateValue(final Uri videoUri){

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String videoConverted = convertMediaUriToPath(getApplicationContext(), videoUri);

                try {
                    heartRate = SlowTask.calculateHeartRate(videoConverted);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(heartRate != null){
                            progressHeartRate.setVisibility(View.GONE);
                            heartRateValueText.setText(heartRate);
                            heartRateValueText.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

}


