package com.example.uflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
      MyConstants constants;
      boolean TourchIsOn = false;
    private static final int CAMERA_REQUEST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constants = new MyConstants();
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
    }

    public void toggle(View v) {
        Button button = (Button) v;
        if (button.getText().equals("Flash On")) {
            button.setText(R.string.flash_off_text);
            FlashOnOff("on");
        } else {
            button.setText(R.string.flash_on_text);
            FlashOnOff("off");
        }


    }

    private void FlashOnOff(String onOff) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String cameraId = null; // Usually back camera is at 0 position.
            try {
                if (camManager != null) {
                    cameraId = camManager.getCameraIdList()[0];
                }
                if (camManager != null) {
                    if (onOff.equals("on")) {
                        camManager.setTorchMode(cameraId, true);   // Turn ON
                        TourchIsOn = true;
                    } else {
                        camManager.setTorchMode(cameraId, false);  // Turn OFF
                        TourchIsOn = false;
                    }
                }
            } catch (CameraAccessException e) {
                e.getMessage();
                Toast.makeText(this, "error"+e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("error", "FlashOnOff: "+e.toString());
            }
        }

    }
}