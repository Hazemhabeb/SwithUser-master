package com.orxtradev.swithuser.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.adapters.OrderAdapter;
import com.orxtradev.swithuser.adapters.OrderPreviewAdapter;
import com.orxtradev.swithuser.model.PreviewOrder;
import com.orxtradev.swithuser.model.maintenanceOrder;
import com.orxtradev.swithuser.utils.SharedPrefDueDate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderPreviewFragment extends Fragment {


    @BindView(R.id.orderRV)
    RecyclerView orderRV;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.empty)
    LinearLayout empty;

    private OrderPreviewAdapter adapter;

    ArrayList<PreviewOrder> orders = new ArrayList<>();
    SharedPrefDueDate pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preview_order, container, false);
        ButterKnife.bind(this, view);


        pref = new SharedPrefDueDate(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);


        adapter = new OrderPreviewAdapter(getContext(), orders);


        orderRV.setAdapter(adapter);
        orderRV.setLayoutManager(layoutManager);


        getProduct();

        return view;
    }


    private void getProduct() {

        loading.setVisibility(View.VISIBLE);

        orders.clear();
        adapter.notifyDataSetChanged();
        DatabaseReference df = FirebaseDatabase.getInstance().getReference();

        df.child("PreviewOrders").orderByChild("userId").equalTo(pref.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            PreviewOrder model = snapshot.getValue(PreviewOrder.class);

                            for (int i = 0; i < orders.size(); i++) {
                                if (orders.get(i).getId().equals(model.getId())) {
                                    orders.remove(i);
                                }
                            }


                            orders.add(model);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);

                } else {
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        });
    }
}
