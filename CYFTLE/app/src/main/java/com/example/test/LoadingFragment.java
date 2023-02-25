package com.example.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;


public class LoadingFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_loading, null);

        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.wait);
        mediaPlayer.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }
}
