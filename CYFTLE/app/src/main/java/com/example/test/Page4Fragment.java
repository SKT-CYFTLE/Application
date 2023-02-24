package com.example.test;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.squareup.picasso.Picasso;

import java.util.List;


public class Page4Fragment extends Fragment {
    private TextView text;
    private ImageView duck_image;
    private SharedViewModel sharedViewModel;
    private String url;
    public Page4Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page4, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        text = (TextView) view.findViewById(R.id.duck_tale);
        // textview 스크롤 가능하게 한다
        text.setMovementMethod(new ScrollingMovementMethod());
        sharedViewModel.getText4().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text.setText(s);
            }
        });
        sharedViewModel.getArr().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> urlList) {
                url = urlList.get(3);

                duck_image = view.findViewById(R.id.ducktale_image);
                Picasso.get().load(url).into(duck_image);
            }
        });

        return view;
    }
}
