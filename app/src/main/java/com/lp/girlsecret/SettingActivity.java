package com.lp.girlsecret;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lp.myapp.R;

/**
 * Created by lp on 2015/7/14.
 */
public class SettingActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.girl_setting);

        Button play = (Button) findViewById(R.id.play);
        Button introduce = (Button) findViewById(R.id.introduce);
        Button author = (Button) findViewById(R.id.author);
        Button exit = (Button) findViewById(R.id.exit);

        play.setOnClickListener(this);
        introduce.setOnClickListener(this);
        author.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.play:
                Intent intent = new Intent();
                intent.setClass(this, GameHomeActivity.class);
                startActivity(intent);
                break;
            case R.id.introduce:
                break;
            case R.id.author:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setIcon(R.drawable.ic_launcher).setMessage("Author: PengL").setPositiveButton("OK", null).show();
                break;
            case R.id.exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.ic_launcher).setMessage("你确定要退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addCategory(Intent.CATEGORY_HOME);
//                                AseoZdpAseo.initFinalTimer(SettingActivity.this);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
        }

    }
}
