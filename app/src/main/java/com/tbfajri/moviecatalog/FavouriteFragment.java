package com.tbfajri.moviecatalog;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import static com.tbfajri.moviecatalog.R.string.title_movie;
import static com.tbfajri.moviecatalog.R.string.title_tv;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        ViewPager viewPager = view.findViewById(R.id.view_pager_id);
        setupViewPager(viewPager);

        // setting tablayout
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPagerAdapter.addFragment(new Fragment_movie(), getString(title_movie));
        viewPagerAdapter.addFragment(new Fragment_movie(), getString(title_tv));
        viewPager.setAdapter(viewPagerAdapter);

    }

}
