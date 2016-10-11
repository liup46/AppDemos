package com.lp.myapp;

import com.lp.FancyCoverFlow.FancyCoverMainActivity;

import com.lp.FlipDeleteListItem.FlipDeleteActivity;
import com.lp.circlering.CircleButtonActivity;
import com.lp.girlsecret.GirlSecretActivity;
import com.lp.rotation.RotationActivity;
import com.lp.slideviewpagebasic.SlideBasicActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView mListView;
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.demo_list);

        data = getResources().getStringArray(R.array.demo_list_title);
        mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.demo_list_item, data));

        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = ((TextView) view).getText().toString();
        if (text.equals(data[0])) {
            startActivity(new Intent(this, MyPermisson.class));
        } else if (text.equals(data[1])) {
            startActivity(new Intent(this, RecyclerActivity.class));
        }else if (text.equals(data[2])) {
            startActivity(new Intent(this, FlipDeleteActivity.class));
        }else if (text.equals(data[3])) {
            startActivity(new Intent(this, ActionbarViewPage.class));
        }else if (text.equals(data[4])) {
            startActivity(new Intent(this, RotationActivity.class));
        }else if (text.equals(data[5])) {
            startActivity(new Intent(this, SlideBasicActivity.class));
        }else if (text.equals(data[6])) {
            startActivity(new Intent(this, com.lp.FlipfinishActivity.FlipDeleteActivity.class));
        }else if (text.equals(data[7])) {
            startActivity(new Intent(this, GirlSecretActivity.class));
        }else if (text.equals(data[8])) {
            startActivity(new Intent(this, FancyCoverMainActivity.class));
        }else if (text.equals(data[9])) {
            startActivity(new Intent(this, CircleButtonActivity.class));
        }else if (text.equals(data[10])) {
//            startActivity(new Intent(this, RippleActivity.class));
        }
    }

}
