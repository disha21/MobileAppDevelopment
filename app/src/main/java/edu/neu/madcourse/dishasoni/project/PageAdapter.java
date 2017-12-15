package edu.neu.madcourse.dishasoni.project;

import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by dishasoni on 11/19/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class PageAdapter  extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    ManageSettingsFragment manageSettingsFragment;
    AddNewFragment addNewFragment;


    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

        manageSettingsFragment = new ManageSettingsFragment();
        addNewFragment = new AddNewFragment();
        addNewFragment.setManagedSettingFragment(manageSettingsFragment);

    }



    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.d("switchcase", "0");
                return manageSettingsFragment;

            case 1:
//                AddNewFragment tab2 = new AddNewFragment();
                return addNewFragment;
            default:
//                ManageSettingsFragment tab3 = new ManageSettingsFragment();
                return manageSettingsFragment;

        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}