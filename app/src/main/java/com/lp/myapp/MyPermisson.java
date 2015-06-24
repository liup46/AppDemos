package com.lp.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;


public class MyPermisson extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_permisson);
        textView = (TextView) findViewById(R.id.clicktotest_permissson);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCamera();
            }
        });

    }

    private void getCamera() {
//        if(Build.VERSION.CODENAME.equals("MNC")){
//            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                openCamera();
//            }else {
//                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
//            }
//
//        }else {
//            openCamera();
//        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(this, "Already have Permisson",Toast.LENGTH_LONG);
//            openCamera();
//        }else {
//            Toast.makeText(this, "You Resfuse this permisson.", Toast.LENGTH_LONG);
//        }
//    }

    private void openCamera() {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1009);

    }
}
