package com.example.test;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class Page1Fragment extends Fragment {

    private String responseString;
    private TextView duck;
    private SharedViewModel sharedViewModel;
    private JSONObject jsonObject;

    // stt로 가져온 데이터 서버로 보내기
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                duck.setText(s);

                try{
                    jsonObject = new JSONObject(s);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonObject != null) {
                    String firstKey = "";
                    String firstValue = "";
                    Iterator<String> keys = jsonObject.keys();

                    if(keys.hasNext()) {
                        firstKey = keys.next();
                        try {
                            firstValue = jsonObject.getString(firstKey);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page1, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        ImageView duck_image = (ImageView) view.findViewById(R.id.ducktale_image);

        duck = (TextView) view.findViewById(R.id.duck_tale);
        // textview 스크롤 가능하게 한다
        duck.setMovementMethod(new ScrollingMovementMethod());


        return view;
    }
}