package com.lp.girlsecret;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.lp.myapp.R;

/**
 * Created by lp on 2015/7/14.
 */
public class GameHomeActivity extends Activity implements ViewSwitcher.ViewFactory, AdapterView.OnItemSelectedListener {

    Gallery gallery;
    ImageSwitcher imageSwitcher;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.girl_game);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.iamgeswitch);
        gallery = (Gallery) findViewById(R.id.gallery);
        imageSwitcher.setFactory(this);
        gallery.setAdapter(new MyAdapter(this));
        gallery.setOnItemSelectedListener(this);

        imageSwitcher.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(GameHomeActivity.this).setIcon(R.drawable.ic_launcher)
                        .setMessage("Are you sure to delete this girl?")
                        .setPositiveButton("Yes, I do", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra("position", mPosition);
                                intent.setClass(GameHomeActivity.this, RemoveActivity.class);
                                GameHomeActivity.this.startActivity(intent);
                            }
                        }).show();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(p.x, p.y - 120));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return imageView;
        //getWindowManager().getDefaultDisplay().getWidth(),
        //getWindowManager().getDefaultDisplay().getHeight() - 120););
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int drawableId;
        try {
            drawableId = R.drawable.class.getDeclaredField("g" + (position + 1) + "_up").getInt(this);
            this.mPosition = position;
            imageSwitcher.setImageResource(drawableId);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    class MyAdapter extends BaseAdapter {
        private Context context;

        MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 21;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            int drawableId = 0;
            try {
                drawableId = R.drawable.class.getDeclaredField("g" + (position + 1) + "_icon").getInt(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            imageView.setLayoutParams(new Gallery.LayoutParams(120, 120));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(drawableId);

            return imageView;
        }
    }

}
