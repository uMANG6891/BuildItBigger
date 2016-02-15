package com.umang.builditbigger.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.umang.builditbigger.R;
import com.umang.builditbigger.ui.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new MainFragment())
                    .commit();
        }
    }
}


