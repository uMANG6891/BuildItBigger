package com.umang.builditbigger.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;

import com.jokes.Joker;
import com.umang.builditbigger.R;
import com.umang.builditbigger.data.EndpointsAsyncTask;
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
        final AsyncTask<Pair<Context, EndpointsAsyncTask.GotJokeCallback>, Void, String> processGetJoke = new EndpointsAsyncTask();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Loading joke please wait...")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        processGetJoke.cancel(true);
                        dialog.dismiss();
                    }
                })
                .setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        processGetJoke.execute(new Pair<Context, EndpointsAsyncTask.GotJokeCallback>(this, new EndpointsAsyncTask.GotJokeCallback() {
            @Override
            public void done() {
                dialog.dismiss();
                Intent i = new Intent(MainActivity.this, JokeActivity.class);
                i.putExtra(JokeActivity.EXTRA_JOKE, Joker.getNewJoke());
                startActivity(i);
            }
        }));
    }

}


