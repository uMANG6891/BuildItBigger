package com.umang.builditbigger.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.umang.builditbigger.R;
import com.umang.builditbigger.data.EndpointsAsyncTask;
import com.umang.builditbigger.ui.activity.MainActivity;
import com.umang.jokedisplay.ui.activity.JokeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by umang on 15/02/16.
 */
public class MainFragment extends Fragment {

    FragmentActivity con;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.adView)
    AdView mAdView;

    private InterstitialAd mInterstitialAd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        con = getActivity();
        ButterKnife.bind(this, view);

        ((MainActivity) con).setSupportActionBar(toolbar);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(con);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        requestNewInterstitial();

        return view;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    @OnClick(R.id.main_b_show_joke)
    void showNewJoke() {
        final AsyncTask<EndpointsAsyncTask.GotJokeCallback, Void, String> processGetJoke = new EndpointsAsyncTask();

        ProgressBar pb = new ProgressBar(con);
        pb.setIndeterminate(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage(R.string.loading_joke_wait_titile)
                .setView(pb)
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

        processGetJoke.execute(new EndpointsAsyncTask.GotJokeCallback() {
            @Override
            public void done(final String result, boolean error) {
                dialog.dismiss();
                if (error) {
                    Log.e("error text", result);
                    Toast.makeText(con, result, Toast.LENGTH_SHORT).show();
                } else {
                    mInterstitialAd.setAdListener(new AdListener() {

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            super.onAdFailedToLoad(errorCode);
                            requestNewInterstitial();
                            showJokeOnUI(result);
                        }

                        @Override
                        public void onAdClosed() {
                            requestNewInterstitial();
                            showJokeOnUI(result);
                        }
                    });
                    mInterstitialAd.show();
                }
            }
        });
    }

    private void showJokeOnUI(String result) {
        Intent i = new Intent(con, JokeActivity.class);
        i.putExtra(JokeActivity.EXTRA_JOKE, result);
        startActivity(i);
    }

}
