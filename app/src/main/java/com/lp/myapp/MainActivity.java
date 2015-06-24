package com.lp.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.testmpermisson:
                startActivity(new Intent(this, MyPermisson.class));
                break;
            case R.id.testRecycler:
                startActivity(new Intent(this, RecyclerActivity.class));
                break;

            case R.id.show_flip_delete:
                startActivity(new Intent(this, FlipDeleteActivity.class));
                break;
            case R.id.actionbar_viewpager:
                startActivity(new Intent(this, ActionbarViewPage.class));
                break;

            case R.id.rotation_test:
                startActivity(new Intent(this, com.lp.rotation.RotationActivity.class));
                break;
            default:
                break;
        }
    }

}
