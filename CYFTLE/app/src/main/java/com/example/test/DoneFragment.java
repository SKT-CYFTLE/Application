package com.example.test;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.DialogFragment;


public class DoneFragment extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done, container, false);

        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.done);
        mediaPlayer.start();

        return view;
    }
}
