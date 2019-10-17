package com.gofishfarm.htkj.ui.main.attention;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class AtentionPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public AtentionPagerFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return AttentionLeftFragment.newInstance();
        } else {
            return AttentionRightFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
