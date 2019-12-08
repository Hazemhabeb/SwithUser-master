package com.orxtradev.swithuser.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.adapters.OrderAdapter;
import com.orxtradev.swithuser.adapters.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {



    //init the views
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myorders, container, false);
        ButterKnife.bind(this, view);


        //init the views

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);



        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OrderMaintenanceFragment(), "طلبات الصيانة");
        adapter.addFragment(new OrderPreviewFragment(), "طلبات المعاينة");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

    }



}
