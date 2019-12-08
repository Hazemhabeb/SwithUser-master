package com.orxtradev.swithuser.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.adapters.OrderAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    @BindView(R.id.finishedRV)
    RecyclerView finishedRV;
    @BindView(R.id.inWorkRV)
    RecyclerView inWorkRV;
    @BindView(R.id.waitingRV)
    RecyclerView waitingRV;
    @BindView(R.id.loading)
    ProgressBar loading;


    private OrderAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext());
        adapter = new OrderAdapter(getContext(),new ArrayList<>());
        finishedRV.setLayoutManager(layoutManager1);
        inWorkRV.setLayoutManager(layoutManager2);
        waitingRV.setLayoutManager(layoutManager3);
        finishedRV.setAdapter(adapter);
        inWorkRV.setAdapter(adapter);
        waitingRV.setAdapter(adapter);

        return view;
    }

}
