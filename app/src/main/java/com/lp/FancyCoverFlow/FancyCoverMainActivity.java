package com.lp.FancyCoverFlow;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lp.myapp.R;

/**
 * Created by lp on 2015/7/16.
 */
public class FancyCoverMainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setListAdapter(new ExampleAdapter());
    }

    private static class ExampleAdapter extends BaseAdapter {
        private final Class[] exampleActivities = new Class[]{SimpleExampleActivity.class, ViewGroupExampleActivity.class, ViewGroupReflectionExampleActivity.class, XmlInflateExampleActivity.class};

        @Override
        public int getCount() {
            return exampleActivities.length;
        }

        @Override
        public Class getItem(int position) {
            return exampleActivities[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final TextView textView;

            if (convertView != null) {
                textView = (TextView) convertView;
            } else {
                Context context = parent.getContext();
                int listItemPadding = context.getResources().getDimensionPixelSize(R.dimen.mainActivityListItemPadding);
                textView = new TextView(parent.getContext());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                int height = context.getResources().getDimensionPixelOffset(R.dimen.mainActivityListItemHeight);

                textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, height));
                textView.setPadding(listItemPadding, 0, listItemPadding, 0);
            }

            final Class activity = getItem(position);
            textView.setText(activity.getSimpleName());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(textView.getContext(), activity);
                    textView.getContext().startActivity(i);

                }
            });

            return textView;
        }
    }
}
