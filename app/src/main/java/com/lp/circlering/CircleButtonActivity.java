package com.lp.circlering;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lp.myapp.R;

/**
 * Created by pengliu2 on 7/16/15.
 */
public class CircleButtonActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_button_activity);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    public static class PlaceholderFragment extends Fragment implements View.OnClickListener
    {

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            final View rootView = inflater.inflate(R.layout.circle_button_fragment, container, false);

            rootView.findViewById(R.id.button0).setOnClickListener(this);
            rootView.findViewById(R.id.button1).setOnClickListener(this);
            rootView.findViewById(R.id.button2).setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onClick(View view)
        {
            Toast.makeText(getActivity(), "Button clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
