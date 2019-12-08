package com.orxtradev.swithuser.fragments;

import android.content.Intent;
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
import com.orxtradev.swithuser.activities.TrackActivity;
import com.orxtradev.swithuser.activities.MaintanceActivity;
import com.orxtradev.swithuser.activities.PreviewActivity;
import com.orxtradev.swithuser.adapters.OfferAdapter;
import com.orxtradev.swithuser.adapters.ProductAdapter;
import com.orxtradev.swithuser.model.Offers;
import com.orxtradev.swithuser.model.equiment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    //init the views
    @BindView(R.id.productRV)
    RecyclerView productRV;
    @BindView(R.id.offerRV)
    RecyclerView offerRV;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.homeParent)
    LinearLayout homeParent;



    private ProductAdapter productAdapter;
    private OfferAdapter offferAdapter;

    private ArrayList<equiment> dataProduct = new ArrayList<>();
    private ArrayList<Offers> dataOffer = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        //init the views


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);

        productAdapter = new ProductAdapter(getContext(),dataProduct);


        productRV.setLayoutManager(layoutManager);
        productRV.setAdapter(productAdapter);




        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);

        offferAdapter = new OfferAdapter(getContext(),dataOffer);

        offerRV.setLayoutManager(layoutManager1);
        offerRV.setAdapter(offferAdapter);


        getProduct();
        getOffer();

        return view;
    }


    @OnClick(R.id.previewCard) void PreviewAction(){
        startActivity(new Intent(getContext(), PreviewActivity.class));

    }

    @OnClick(R.id.maintanceCard)void maintanceAction(){
        startActivity(new Intent(getContext(), MaintanceActivity.class));
    }




    //todo to get the data from the server

    private void getProduct(){

        loading.setVisibility(View.VISIBLE);
        homeParent.setVisibility(View.GONE);
        dataProduct.clear();
        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        df.child("Equipment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            equiment model = snapshot.getValue(equiment.class);
                            dataProduct.add(model);
                            productAdapter.notifyDataSetChanged();
                        }
                    }
                    loading.setVisibility(View.GONE);
                    homeParent.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        });
    }

    private void getOffer(){

        loading.setVisibility(View.VISIBLE);
        homeParent.setVisibility(View.GONE);
        dataOffer.clear();

        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        df.child("Offers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            Offers model = snapshot.getValue(Offers.class);
                            dataOffer.add(model);
                            offferAdapter.notifyDataSetChanged();
                        }
                    }
                    loading.setVisibility(View.GONE);
                    homeParent.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        });
    }


    @OnClick(R.id.followCard)void followAction(){
        startActivity(new Intent(getContext(), TrackActivity.class));
    }




}
