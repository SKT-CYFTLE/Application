package com.example.test;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class PagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments;
    private static final int NUM_PAGES = 3;

    public PagerAdapter(FragmentActivity fragmentActivity, List<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) { //포지션마다 있을 fragment설정
        if (position == 0) return new Duck1Fragment();
        else if (position == 1) return new Duck2Fragment();
        else return new Duck3Fragment();
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;  //페이지 수 지정.
    }
}
