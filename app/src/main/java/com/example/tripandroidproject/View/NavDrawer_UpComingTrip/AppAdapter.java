package com.example.tripandroidproject.View.NavDrawer_UpComingTrip;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();

    public AppAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addFragment (Fragment fragment){
        fragments.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
