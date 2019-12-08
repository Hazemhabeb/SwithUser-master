package com.orxtradev.swithuser.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.orxtradev.swithuser.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Intro2Fragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro2, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
