package com.umang.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jokes.Joker;
import com.umang.jokedisplay.ui.activity.JokeActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.main_b_show_joke)
    void getNewJoke() {
        Intent i = new Intent(this, JokeActivity.class);
        i.putExtra(JokeActivity.EXTRA_JOKE, Joker.getNewJoke());
        startActivity(i);
    }
}
