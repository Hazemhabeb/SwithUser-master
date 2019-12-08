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
import com.orxtradev.swithuser.adapters.NotificationAdapter;
import com.orxtradev.swithuser.model.NotificationModel;
import com.orxtradev.swithuser.model.maintenanceOrder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.emptyParent)
    LinearLayout emptyParent;

    private NotificationAdapter adapter;
    ArrayList<NotificationModel> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new NotificationAdapter(getContext(),data);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        getProduct();
        return view;
    }



    private void getProduct(){

        loading.setVisibility(View.VISIBLE);

        data.clear();
        adapter.notifyDataSetChanged();
        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        df.child("Notification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                data.clear();
                adapter.notifyDataSetChanged();
                emptyParent.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            NotificationModel model = snapshot.getValue(NotificationModel.class);



                            for (int i = 0; i < data.size(); i++) {
                                if (data.get(i).getId().equals(model.getId())) {
                                    data.remove(i);
                                }
                            }

                            assert model != null;
                            if (model.getType().equals("1"))
                                continue;

                            if (model.getType().equals("2"))
                                continue;


                            if (model.getStatus().equals("1")){
                                continue;
                            }

                            data.add(model);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    loading.setVisibility(View.GONE);

                    if (data.size()==0){
                        emptyParent.setVisibility(View.VISIBLE);
                    }else {
                        emptyParent.setVisibility(View.GONE);
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        });
    }

}
