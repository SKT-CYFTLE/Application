package com.example.test;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class PigPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments;
    private static final int NUM_PAGES = 4;

    public PigPagerAdapter(FragmentActivity fragmentActivity, List<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) { //포지션마다 있을 fragment설정
        if (position == 0) return new Pig1Fragment();
        else if (position == 1) return new Pig2Fragment();
        else if (position == 2) return new SttFragment();
        else return new Pig3Fragment();
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;  //페이지 수 지정.
    }
}

