package com.gendigital.gabywear.actividad_principal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gaby on 07/07/2016.
 */
public class PagerAdaptador extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private HashMap<Integer, String> mapFragmentTags;
    private FragmentManager fm;

    public PagerAdaptador (FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
        mapFragmentTags = new HashMap<Integer, String>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment) {
            // record the fragment tag here.
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mapFragmentTags.put(position, tag);
        }
        return obj;
    }
    public Fragment getFragment(int position) {
        String tag = mapFragmentTags.get(position);
        if (tag == null)
            return null;
        return fm.findFragmentByTag(tag);
    }
}
