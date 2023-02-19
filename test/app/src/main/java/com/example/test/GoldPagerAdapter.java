package com.example.test;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class GoldPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments;
    private static final int NUM_PAGES = 4;

    public GoldPagerAdapter(FragmentActivity fragmentActivity, List<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) { //포지션마다 있을 fragment설정
        if (position == 0) return new Gold1Fragment();
        else if (position == 1) return new Gold2Fragment();
        else if (position == 2) return new SttFragment();
        else return new Gold3Fragment();
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;  //페이지 수 지정.
    }
}

