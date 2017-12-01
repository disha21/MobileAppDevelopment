package edu.neu.madcourse.dishasoni.project;

import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by dishasoni on 11/19/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter  extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ManageSettingsFragment tab1 = new ManageSettingsFragment();
                return tab1;
            case 1:
                AddNewFragment tab2 = new AddNewFragment();
                return tab2;
            default:
                ManageSettingsFragment tab3 = new ManageSettingsFragment();
                return tab3;

        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}