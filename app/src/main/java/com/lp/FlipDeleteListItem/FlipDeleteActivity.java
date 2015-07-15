package com.lp.FlipDeleteListItem;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;

import com.lp.myapp.R;

import java.util.ArrayList;


public class FlipDeleteActivity extends ActionBarActivity {

    MyListView myListView;
    int length = 10;
    ArrayList<String> listStr = new ArrayList<String>(length);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_flip_delete);
        myListView = (MyListView) findViewById(R.id.list_view);
        iniData();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_view_item, R.id.textview1, listStr);
        myListView.setAdapter(adapter);
        myListView.setOnDeleteListener(new MyListView.OnDeleteListener() {
            @Override
            public void onDelete(int index) {

                listStr.remove(index);
                adapter.notifyDataSetChanged();

            }
        });

    }


    void iniData() {
        for (int i = 0; i < length; i++) {
            listStr.add("Hello world " + i);
        }
    }

}
