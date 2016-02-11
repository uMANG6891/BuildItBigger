package com.umang.jokedisplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umang.jokedisplay.R;
import com.umang.jokedisplay.ui.activity.JokeActivity;

/**
 * Created by umang on 11/02/16.
 */
public class JokeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_joke, container, false);
        ((TextView) view.findViewById(R.id.joke_frag_tv_joke)).setText(getArguments().getString(JokeActivity.EXTRA_JOKE));
        return view;
    }
}
