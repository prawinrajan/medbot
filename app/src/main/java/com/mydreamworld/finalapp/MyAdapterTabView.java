package com.mydreamworld.finalapp;



import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapterTabView extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyAdapterTabView(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Allnews allnewsfragment = new Allnews();
                return allnewsfragment;
            case 1:
                Hotnews hotnewsfragment = new Hotnews();
                return hotnewsfragment;
            case 2:
                Likes likesfragemnt = new Likes();
                return likesfragemnt;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }


}
