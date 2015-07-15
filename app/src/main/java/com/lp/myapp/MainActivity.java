package com.lp.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.lp.FancyCoverFlow.FancyCoverMainActivity;
import com.lp.FlipDeleteListItem.FlipDeleteActivity;
import com.lp.girlsecret.GirlSecretActivity;


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
            case R.id.flip_delete:
                startActivity(new Intent(this, com.lp.FlipfinishActivity.FlipDeleteActivity.class));
                break;

            case R.id.girl_secret:
                startActivity(new Intent(this, GirlSecretActivity.class));
                break;

            case R.id.Fancy_Cover_Flow:
                startActivity(new Intent(this, FancyCoverMainActivity.class));
                break;
            default:
                break;
        }
    }

}
