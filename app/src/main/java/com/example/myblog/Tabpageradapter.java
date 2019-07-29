package com.example.myblog;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Tabpageradapter extends FragmentPagerAdapter {

private int tabCount;

    public Tabpageradapter(FragmentManager fm,int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Newpost();
            case 1:
                return new Viewblog();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
