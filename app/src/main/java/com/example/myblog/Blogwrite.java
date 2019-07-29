package com.example.myblog;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class Blogwrite extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public Blogwrite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_blogwrite, container, false);
        tabLayout  = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);

        tabLayout.addTab(tabLayout.newTab().setText("NewPost"));
        tabLayout.addTab(tabLayout.newTab().setText("All BlogPost"));
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(Color.WHITE,Color.BLUE);

        Tabpageradapter tabpageradapter = new Tabpageradapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(tabpageradapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return view;
    }


}
