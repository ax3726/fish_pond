package com.gofishfarm.htkj.ui.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gofishfarm.htkj.ui.main.RankingLeftFragment;
import com.gofishfarm.htkj.ui.main.RankingRightFragment;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class RankingPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public RankingPagerFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return RankingLeftFragment.newInstance();
        } else {
            return RankingRightFragment.newInstance();
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
