package com.lp.myapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerActivity extends ActionBarActivity {

    private RecyclerView rv;

    private int length = 1000;

    private List<CellData> datas = new ArrayList<CellData>();

    private void initData() {
        for (int i = 0; i < length; i++) {
            datas.add(new CellData("title" + i, "content" + i));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

        setContentView(R.layout.activity_recycler);
        rv = (RecyclerView) findViewById(R.id.recycleview);


//        rv = new RecyclerView(this);
//        setContentView(rv);
//        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv.setLayoutManager(new GridLayoutManager(this, 3));


        rv.setAdapter(new MyAdapter());
    }


    private class MyAdapter extends RecyclerView.Adapter {

        class ViewHolder extends RecyclerView.ViewHolder {
            private View root;
            TextView titleTV;
            TextView contentTv;

            public ViewHolder(Context context, int rescource) {
                this(LayoutInflater.from(context).inflate(rescource, null));

            }

            public ViewHolder(View itemView) {
                super(itemView);
                root = itemView;
                titleTV = (TextView) root.findViewById(R.id.rec_title);
                contentTv = (TextView) root.findViewById(R.id.rec_textContent);

            }

            public void updateData(CellData data) {
                titleTV.setText(data.title);
                contentTv.setText(data.content);
            }

        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent.getContext(), R.layout.recycler_item);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder vh = (ViewHolder) holder;
            vh.updateData(datas.get(position));

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    private class CellData {
        public String title;
        public String content;

        CellData(String tit, String content) {
            title = tit;
            this.content = content;
        }

    }
}
