package com.davidremington.myrestaurants.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidremington.myrestaurants.R;
import com.davidremington.myrestaurants.adapters.FirebaseRestaurantViewHolder;
import com.davidremington.myrestaurants.models.Restaurant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.davidremington.myrestaurants.Constants.FIREBASE_CHILD_RESTAURANTS;

public class SavedRestaurantListActivity extends BaseActivity {
    private DatabaseReference restaurantReference;
    private FirebaseRecyclerAdapter firebaseAdapter;

    @BindView(R.id.restaurantsRecyclerView) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

        restaurantReference = FirebaseDatabase.getInstance().getReference().child(FIREBASE_CHILD_RESTAURANTS);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        FirebaseRecyclerOptions<Restaurant> options = buildRecyclerOptions();
        firebaseAdapter = new FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder>(options) {

            @Override
            public FirebaseRestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.restaurant_list_item, parent, false);
                return new FirebaseRestaurantViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(FirebaseRestaurantViewHolder viewHolder, int position, Restaurant model) {
                viewHolder.bindRestaurant(model);
            }

        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(firebaseAdapter);
    }

    private FirebaseRecyclerOptions<Restaurant> buildRecyclerOptions() {
           return new FirebaseRecyclerOptions.Builder<Restaurant>()
                   .setQuery(restaurantReference, Restaurant.class)
                   .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAdapter.stopListening();
    }


}
