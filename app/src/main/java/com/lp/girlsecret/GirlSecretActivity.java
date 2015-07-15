package com.lp.girlsecret;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lp.myapp.R;

/**
 * Created by lp on 2015/7/14.
 */
public class GirlSecretActivity extends Activity implements View.OnClickListener {

    Button enter;
    Button cancel;
    EditText passwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.girl_secret);
        initViews();
    }

    private void initViews() {
        enter = (Button) findViewById(R.id.enter);
        cancel = (Button) findViewById(R.id.cancel);
        enter.setOnClickListener(this);
        cancel.setOnClickListener(this);
        passwd = (EditText) findViewById(R.id.password);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.enter) {
            String password = passwd.getText().toString();
            savePassword(password);
        } else if (v.getId() == R.id.cancel) {
            finish();
        }
    }

    void savePassword(String passStr) {
        SharedPreferences sp = getSharedPreferences("password", MODE_PRIVATE);
        String pass = sp.getString("password", "");
        if ("".equals(pass) && !passStr.isEmpty()) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("password", passStr);
            editor.commit();
            startActivity(new Intent(this, SettingActivity.class));
            finish();
        } else {
            if (pass.equals(passStr)) {
                startActivity(new Intent(this, SettingActivity.class));
            } else {
                Toast.makeText(this, "Password is incorrect!", Toast.LENGTH_LONG).show();
            }

        }


    }
}
